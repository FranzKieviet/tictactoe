package com.tictaccode.tictactoeclient;

import javafx.scene.text.Font;

import java.util.Objects;

public class Fonts {
    public static final Font GAME_FONT = Font.loadFont(
            Objects.requireNonNull(Fonts.class.getResource("Chalkduster.ttf")).toString(), 30);
}
