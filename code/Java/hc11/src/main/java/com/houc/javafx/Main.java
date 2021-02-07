package com.houc.javafx;

import com.houc.javafx.layout.SameSizeButtonApplication;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.debug("******** start ********");
         /*Application.launch(UIAlignmentApplication.class, args);*/
        Application.launch(SameSizeButtonApplication.class, args);
        log.debug("******** end ********");
    }

}
