package pt.ipleiria.knowestgbygame.Helpers;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class HelperMethods {

    public static AnswerType getCategory(int position) {
        switch (position) {
            case 1:
                return AnswerType.NUMBER;
            case 2:
                return AnswerType.TEXT;
            case 3:
                return AnswerType.QRCODE;
            case 4:
                return AnswerType.OBJECTDETECTION;
            default:
                return AnswerType.TEXT;
        }
    }



    public static int getPositionByAnswerType(AnswerType type) {
        switch (type) {
            case NUMBER:
                return 1;
            case TEXT:
                return 2;
            case QRCODE:
                return 3;
            case OBJECTDETECTION:
                return 4;
            default:
                return 2;
        }
    }




}
