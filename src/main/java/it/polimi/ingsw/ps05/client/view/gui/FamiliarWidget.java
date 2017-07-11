package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarData.FAMILIAR_DATA;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by miotto on 28/06/17.
 */
public class FamiliarWidget extends ImageView{

    private boolean placed;
    ColorEnumeration playerColor;
    ColorEnumeration familiarColor;
    private String imagePath;
    private FamiliarData familiarData;
    private Image img;

    public FamiliarWidget(ColorEnumeration playerColor, ColorEnumeration familiarColor) {
        // construct image path
        this.familiarData = new FamiliarData(playerColor, familiarColor);
        this.playerColor = playerColor;
        this.familiarColor = familiarColor;

        this.imagePath = path + this.playerColor.toString().toLowerCase() + "pl/" + this.familiarColor.toString().toLowerCase() + ".png";

        addImage(this.imagePath);

        setupGestureSource();
    }

    public void addImage(String path) {
        File crDir = new File(path);
        try{
            this.img = new Image(crDir.toURI().toURL().toString(), FAMILIAR_MIN_SIZE * resize, FAMILIAR_MIN_SIZE * resize, true, true);
            this.setImage(img);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
    
    private void getAllFile(File crDir){
    	File[] fileList = crDir.listFiles();
    	for (File f : fileList){
    		if (f.isDirectory()){
    			getAllFile(f);
    		}
    		if (f.isFile()){
    			System.out.println(f.getPath());
    		}
    	}
    }

    void setupGestureSource() {

        this.setOnDragDetected((MouseEvent e) -> {

            this.setCursor(Cursor.CLOSED_HAND);

            /* drag was detected, start a drag-and-drop gesture */
            /* allow MOVE transfer mode */
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);

            /* Put the data in the dragboard */
            ClipboardContent content = new ClipboardContent();
            FamiliarData data = new FamiliarData(this.playerColor, this.familiarColor, this.getImagePath());
            System.out.println("dati inseriti nella dragboard");
            System.out.println("playercolor: " + data.getPlayerColor());
            System.out.println("familiarcolor: " + data.getFamiliarColor());
            System.out.println("imagepath: " + data.getFamiliarImagePath());
            content.put(FAMILIAR_DATA, data);
            db.setContent(content);

            /*
            Image imageElementImage = this.getImage();
            content.putImage(imageElementImage);
            db.setContent(content);
            */

            //e.consume();

        });

        this.setOnMouseEntered((MouseEvent e) -> {
            this.setCursor(Cursor.HAND);
        });

        this.setOnDragDone((DragEvent e) -> {
            /* Actions to be performed after the drag is completed */
            if (e.getTransferMode() == TransferMode.MOVE) {
                this.setImage(null);
            }

            //e.consume();
        });

    }

    public void repaint() {
        if(!isPlaced()) {
            //TODO
        }
    }

    public ImageView getImageElement() {
        return this;
    }

    public ColorEnumeration getFamiliarColor() {
        return familiarColor;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public String getImagePath() {
        return imagePath;
    }
}
