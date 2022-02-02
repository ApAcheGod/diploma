package com.example.diplom.services;

import com.example.diplom.entities.User;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateLoginService {

    private final UserServiceImpl userServiceImpl;

    String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);

    public void createLoginForUser(User user){

        int countOfBodies = userServiceImpl.numberOfBodies(user);
        char first_name = user.getFirst_name().charAt(0);
        String last_name = user.getLast_name();
        String patronymic = ((user.getPatronymic().length() > 2) ? user.getPatronymic().substring(0,3) : "");

        if (countOfBodies == 0){
            user.setLogin(toLatinTrans.transliterate(
                    first_name + patronymic + last_name));
        }else{
            user.setLogin(toLatinTrans.transliterate(
                    first_name + patronymic + last_name + countOfBodies));
        }
    }
}
