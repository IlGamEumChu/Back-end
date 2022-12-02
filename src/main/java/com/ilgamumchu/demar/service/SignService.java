package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.domain.Music;
import com.ilgamumchu.demar.domain.PlayListTrack;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.domain.UserRole;
import com.ilgamumchu.demar.dto.SignUpRequestDTO;
import com.ilgamumchu.demar.dto.LoginUserDTO;

import com.ilgamumchu.demar.dto.UserResponseDTO;
import com.ilgamumchu.demar.repository.MusicRepository;
import com.ilgamumchu.demar.repository.PlayListTrackRepository;
import com.ilgamumchu.demar.repository.UserRepository;
import com.ilgamumchu.demar.utils.exception.SpotifyExecption;
import com.ilgamumchu.demar.utils.exception.UserEmailAlreadyExistsException;
import com.ilgamumchu.demar.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.json.simple.parser.JSONParser;

import java.util.*;

@RequiredArgsConstructor
@Transactional(readOnly = false)
@Service
public class SignService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final PlayListTrackRepository playListTrackRepository;

    private List<String> getMusics(JSONObject parsed){
        List<String> musicList = new ArrayList<>();
        JSONObject currentPlay = (JSONObject) parsed.get("currently_playing");
        musicList.add(currentPlay.get("name").toString());

        JSONArray queue = (JSONArray) parsed.get("queue");

        for (int i = 0; i< queue.size(); i++) {
            JSONObject queueItem = (JSONObject) queue.get(i);
            musicList.add(queueItem.get("name").toString());
        }

        return musicList;
    }

    private JSONObject parseToJson(String response) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        return jsonObject;
    }

    private List<String> getSpotifyMusicQueue(String sp_token) throws ParseException {
        WebClient client = WebClient.create();
        String response = client.get()
                .uri("https://api.spotify.com/v1/me/player/queue")
                .headers(h -> h.setBearerAuth(sp_token))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject parsed = parseToJson(response);

        return getMusics(parsed);
    }

    public User signup(SignUpRequestDTO signUpRequestDTO){
        List<String> musicList = new ArrayList<>();
        Date now = new Date();

        String email = signUpRequestDTO.getEmail();

        if(userRepository.existsByEmail(email)){
            throw new UserEmailAlreadyExistsException(email);
        }

        String username = signUpRequestDTO.getName();
        String password = passwordEncoder.encode(signUpRequestDTO.getPassword());
        UserRole role = UserRole.ROLE_USER;

        String sp_token = signUpRequestDTO.getSp_token();

        try{
            musicList = getSpotifyMusicQueue(sp_token);
        }catch(Exception e){
            throw new SpotifyExecption();
        }

        User user = new User(null,username, email, password, role, now);
        userRepository.save(user);

        for(int i=0; i < musicList.size(); i++){
            if(musicRepository.existsByTitle(musicList.get(i))){
                Music music = musicRepository.findByTitle(musicList.get(i));
                PlayListTrack playListTrack = new PlayListTrack(null,user,music);
                playListTrackRepository.save(playListTrack);
            }
        }

        return user;
    }

    public User login(LoginUserDTO loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }
        return user;
    }

}
