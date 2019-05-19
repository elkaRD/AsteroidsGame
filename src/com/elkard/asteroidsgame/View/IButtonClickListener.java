package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.View.UI.Button;
import com.elkard.asteroidsgame.View.UI.ButtonsGroup;

public interface IButtonClickListener
{
    void onButtonClicked(ButtonsGroup group, Button clicked);
}
