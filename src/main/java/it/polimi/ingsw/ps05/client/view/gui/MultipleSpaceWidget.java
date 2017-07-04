package it.polimi.ingsw.ps05.client.view.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarWidget.FAMILIAR_MIN_SIZE;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;


/**
 * Created by miotto on 27/06/17.
 */
public class MultipleSpaceWidget implements ActionSpaceWidgetInterface {

    public static final double SCROLLPANE_HEIGHT_RESIZE = 1.2, SCROLLPANE_WIDTH_RESIZE = 8.0;

    private int referenceId;
    private int minDie;
    private boolean isLegal;
    private final ScrollPane scrollPane = new ScrollPane();
    private final HBox hbox = new HBox();


    public MultipleSpaceWidget(int referenceId, int minDie) {
        this(minDie);
        this.referenceId = referenceId;
    }

    public MultipleSpaceWidget(int minDie) {

        this.minDie = minDie;

        hbox.setMinHeight(FAMILIAR_MIN_SIZE * resize);
        hbox.setFillHeight(true);
        scrollPane.setContent(hbox);

        /* disabling scrollbar */
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(false);

        setupGestureTarget();
    }

    public void repaint() {
        /* removing all familiars inside the box */
        this.hbox.getChildren().clear();
    }

    public void setupGestureTarget() {
        /* Setting up drag event handlers */
        setupDragOver();
        setupDragEntered();
        setupDragExited();
        setupDragDropped();
    }

    public void setupDragOver() {
        scrollPane.setOnDragOver((DragEvent e) -> {

            /* source is dragged over the scrollbar */

            if (e.getGestureSource() != scrollPane && e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            //e.consume();
        });
    }

    public void setupDragEntered() {
        scrollPane.setOnDragEntered((DragEvent e) -> {
            if (e.getGestureSource() != scrollPane && e.getDragboard().hasImage()) {
                scrollPane.setStyle("-fx-border-style: outset");
                scrollPane.setStyle("-fx-border-width: 8px");
                scrollPane.setStyle("-fx-border-color: palegreen");
            }

            //e.consume();
        });
    }

    public void setupDragExited() {
        scrollPane.setOnDragExited((DragEvent e) -> {

            scrollPane.setStyle("-fx-border-style: none");
            //e.consume();

        });
    }

    public void setupDragDropped() {
        scrollPane.setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if(e.getDragboard().hasImage()) {
                //TODO comunicare al controller che il familiare è stato posizionato nello spazio multiplo
                System.out.println("inside if");
                Image source = e.getDragboard().getImage();
                ImageView imageElement = new ImageView(source);
                hbox.getChildren().add(imageElement);
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    private static <T> long countChildren(TreeItem<T> treeItem) {
        long count = 0;

        if (treeItem != null) {
            ObservableList<TreeItem<T>> children = treeItem.getChildren();

            if (children != null) {
                count += children.size();

                for (TreeItem<T> child : children) {
                    count += countChildren(child);
                }
            }
        }

        return count;
    }

    private void setLayout() {
        //TODO allargare lo scroll pane quando si aggiunge un elemento
    }

    public int getMinDie() {
        return minDie;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    @Override
    public void setReferenceId(Integer referenceId) {

    }

    public void setId(int referenceId) {
        this.referenceId = referenceId;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean legal) {
        isLegal = legal;
    }

    public void setMinDie(int minDie) {
        this.minDie = minDie;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
