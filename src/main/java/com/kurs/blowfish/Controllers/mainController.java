package com.kurs.blowfish.Controllers;

import com.kurs.blowfish.entity.blowfish;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class mainController {
    @FXML
    public TextArea inputArea;
    @FXML
    public Button bDecrypt;
    @FXML
    public Button bEncrypt;
    @FXML
    public TextArea outputArea;
    @FXML
    public Button bSetKey;
    @FXML
    public TextField fKey;

    blowfish blowfish=new blowfish();
    public void aEncrypt() {

        String text=inputArea.getText();
        outputArea.setText(blowfish.encrypt(text));

    }
    public void aDecrypt()
    {
        String text=inputArea.getText();
        outputArea.setText(blowfish.decrypt(blowfish.converter(text)));
    }

    public void aSetKey() {
        String key=fKey.getText();
        blowfish.newKey(key);
    }
}