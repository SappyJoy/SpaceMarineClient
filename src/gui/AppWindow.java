package gui;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.Client;
import gui.animations.MouseGestures;
import gui.animations.CircleTransition;
import gui.animations.Shaking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import user.GlobalUser;
import user.User;
import utils.AnswerHandler;
import utils.I18N;

public class AppWindow {

    @FXML
    private TableView<SpaceMarineAdapter> table;

    @FXML
    private TableColumn<SpaceMarineAdapter, Integer> id;

    @FXML
    private TableColumn<SpaceMarineAdapter, String> name;

    @FXML
    private TableColumn<SpaceMarineAdapter, Long> x;

    @FXML
    private TableColumn<SpaceMarineAdapter, Long> y;

    @FXML
    private TableColumn<SpaceMarineAdapter, Float> health;

    @FXML
    private TableColumn<SpaceMarineAdapter, Boolean> loyal;

    @FXML
    private TableColumn<SpaceMarineAdapter, String> weapon;

    @FXML
    private TableColumn<SpaceMarineAdapter, String> meleeWeapon;

    @FXML
    private TableColumn<SpaceMarineAdapter, String> chapter;

    @FXML
    private TableColumn<SpaceMarineAdapter, Integer> count;

    @FXML
    private TableColumn<SpaceMarineAdapter, String> world;

    @FXML
    private Button visualButton;

    @FXML
    private ComboBox<String> command;

    @FXML
    public TextArea console;

    @FXML
    private VBox commandValues;

    @FXML
    private Button executeButton;

    @FXML
    private Label userName;

    @FXML
    private Pane langPane;

    @FXML
    private Label commandsLabel;

    private Stage visualStage;

    private boolean isVisualStageShow = false;

    private Set<SpaceMarineAdapter> list;
    private HashMap<Long, Color> userColors;

    private long GRID_X0 = Long.MAX_VALUE;
    private long GRID_Y0 = Long.MAX_VALUE;
    private long GRID_X1 = Long.MIN_VALUE;
    private long GRID_Y1 = Long.MIN_VALUE;
    private float HEALTH_MAX = Float.MIN_VALUE;
    private Label x0Label = new Label();
    private Label y0Label = new Label();
    private Label x1Label = new Label();
    private Label y1Label = new Label();

    private boolean isItemsChanged = false;

    private CommandController currentCommand = null;

    ObservableList<String> commandsList = FXCollections.observableArrayList(
            "help", "info", "show", "insert", "update", "remove_key", "clear", "execute_script",
            "remove_greater", "remove_lower", "print_field_ascending_chapter", "history", "count_by_weapon_type",
            "filter_less_than_health");

    private Task<Boolean> getDataTask;
    private ExecutorService threadPool;

    private Scene currentVisualScene = null;
    private Group innerRoot;
    private Set<Circle> currentCircles = null;
    private FXMLLoader loader;

    @FXML
    void initialize() {
//        User user = new User();
//        user.setLogin("serp");
//        user.setPassword("f9BI3093");
//        user.setRegistered(true);
//        user.setEntry(false);
//        GlobalUser.setUser(user);

        visualButton.textProperty().bind(I18N.createStringBinding("visual"));
        executeButton.textProperty().bind(I18N.createStringBinding("execute"));
        commandsLabel.textProperty().bind(I18N.createStringBinding("commands"));
        name.textProperty().bind(I18N.createStringBinding("name"));
        health.textProperty().bind(I18N.createStringBinding("health"));
        loyal.textProperty().bind(I18N.createStringBinding("loyal"));
        weapon.textProperty().bind(I18N.createStringBinding("weapon"));
        meleeWeapon.textProperty().bind(I18N.createStringBinding("melee_weapon"));
        chapter.textProperty().bind(I18N.createStringBinding("chapter"));
        count.textProperty().bind(I18N.createStringBinding("count"));
        world.textProperty().bind(I18N.createStringBinding("world"));

        threadPool = Executors.newFixedThreadPool(1);
        userColors = new HashMap<>();
        list = new HashSet<>();
        currentCircles = new HashSet<>();
        loader = new FXMLLoader();
        innerRoot = new Group();
        userName.setText(GlobalUser.getUser().getLogin());

        {
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            x.setCellValueFactory(new PropertyValueFactory<>("x"));
            y.setCellValueFactory(new PropertyValueFactory<>("y"));
            health.setCellValueFactory(new PropertyValueFactory<>("health"));
            loyal.setCellValueFactory(new PropertyValueFactory<>("loyal"));
            weapon.setCellValueFactory(new PropertyValueFactory<>("weapon"));
            meleeWeapon.setCellValueFactory(new PropertyValueFactory<>("meleeWeapon"));
            chapter.setCellValueFactory(new PropertyValueFactory<>("chapter"));
            count.setCellValueFactory(new PropertyValueFactory<>("count"));
            world.setCellValueFactory(new PropertyValueFactory<>("world"));
            table.setItems(getSpaceMarines());
        }

        command.setItems(commandsList);

        command.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            // Выбрать нужную таблицу для заполнения команды
            FXMLLoader loader = new FXMLLoader();
            switch (newValue) {
                case "help": case "show": case "info": case "clear":
                    case "history": case "print_field_ascending_chapter": {
                    loader.setLocation(getClass().getResource("/gui/emptyCommand.fxml"));
                    break;
                }
                case "insert": {
                    loader.setLocation(getClass().getResource("/gui/insertCommand.fxml"));
                    break;
                }
                case "update": {
                    loader.setLocation(getClass().getResource("/gui/updateCommand.fxml"));
                    break;
                }
                case "remove_key": {
                    loader.setLocation(getClass().getResource("/gui/removeKeyCommand.fxml"));
                    break;
                }
                case "execute_script": {
                    loader.setLocation(getClass().getResource("/gui/executeScriptCommand.fxml"));
                    break;
                }
                case "remove_greater": case "remove_lower": {
                    loader.setLocation(getClass().getResource("/gui/removeFilterCommand.fxml"));
                    break;
                }
                case "count_by_weapon_type": {
                    loader.setLocation(getClass().getResource("/gui/countByWeaponType.fxml"));
                    break;
                }
                case "filter_less_than_health": {
                    loader.setLocation(getClass().getResource("/gui/filterLessThanHealth.fxml"));
                    break;
                }
            }
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent commandRoot = loader.getRoot();
            currentCommand = loader.getController();
            commandRoot.setLayoutX(20);
            commandRoot.setLayoutY(50);

            commandValues.getChildren().clear();
            commandValues.getChildren().addAll(commandRoot);
        });

        table.setOnMouseClicked(event -> {
            SpaceMarineAdapter sm = table.getSelectionModel().getSelectedItem();
            command.getSelectionModel().select(4);
            ((UpdateCommand) currentCommand).setId(sm.getId() + "");
            ((UpdateCommand) currentCommand).setName(sm.getName());
            ((UpdateCommand) currentCommand).setX(sm.getX() + "");
            ((UpdateCommand) currentCommand).setY(sm.getY() + "");
            ((UpdateCommand) currentCommand).setHealth(sm.getHealth() + "");
            ((UpdateCommand) currentCommand).setLoyal(sm.isLoyal() + "");
            ((UpdateCommand) currentCommand).setWeapon(sm.getWeapon());
            ((UpdateCommand) currentCommand).setMeleeWeapon(sm.getMeleeWeapon());
            ((UpdateCommand) currentCommand).setChapter(sm.getChapter());
            ((UpdateCommand) currentCommand).setCount(sm.getCount() + "");
            ((UpdateCommand) currentCommand).setWorld(sm.getWorld());
        });

        executeButton.setOnAction(event -> {
            String commandName = command.getValue();
            String answer = "";
            try {
                if (commandName != null && currentCommand != null) {
                    answer = Client.getInstance().get(commandName + currentCommand.getInformation());
                    updateTable();
                } else {
                    throw new NoSuchElementException();
                }
            } catch (NoSuchElementException elementException) {
                System.out.println("Wrong input");
                Shaking executeAnimation = new Shaking(executeButton);
                executeAnimation.playAnimation();
            } finally {
                console.setText(answer);
            }
        });

        visualStage = setupVisualStage(loader);
        visualStage.setOnCloseRequest(event -> {
            visualStage.hide();
            isVisualStageShow = !isVisualStageShow;
        });

        x0Label.setLayoutX(90);
        x0Label.setLayoutY(740);
        y0Label.setLayoutX(40);
        y0Label.setLayoutY(720);
        x1Label.setLayoutX(770);
        x1Label.setLayoutY(740);
        y1Label.setLayoutX(40);
        y1Label.setLayoutY(20);

        visualButton.setOnAction(event -> {
            if (isVisualStageShow)
                visualStage.hide();
            else {
                visualStage.show();
                for (Circle circle : currentCircles) {
                    new CircleTransition(circle).playAnimation();
                }
            }

            isVisualStageShow = !isVisualStageShow;
        });

        currentCircles = getCircles();
//        innerRoot.getChildren().addAll(currentCircles);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/langBox.fxml"));
            Parent comboRoot = loader.load();

            langPane.getChildren().add(comboRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }

        getDataTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                boolean flag = true;
                int i = 0;
                while (flag) {
                    updateTable();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Continue
                        System.out.println("Interrupt...");
                    }
//                    System.out.println("New data " + i);
                    i++;
                }
                return false;
            }
        };

        threadPool.submit(getDataTask);
    }

    private void updateTable() {
        ObservableList<SpaceMarineAdapter> newItems = FXCollections.observableArrayList();
        ObservableList<SpaceMarineAdapter> removeItems = FXCollections.observableArrayList();
        ObservableList<SpaceMarineAdapter> currentItems = getSpaceMarines();

        for (SpaceMarineAdapter sm : currentItems) {
            if (!table.getItems().contains(sm)) {
                newItems.add(sm);
            }
        }
        for (SpaceMarineAdapter sm : table.getItems()) {
            if (!currentItems.contains(sm)) {
                removeItems.add(sm);
            }
        }
        table.getItems().removeAll(removeItems);
        table.getItems().addAll(newItems);


    }

    // Get all elements
    public ObservableList<SpaceMarineAdapter> getSpaceMarines() {
//        ObservableList<SpaceMarineAdapter> items = FXCollections.observableArrayList();

        Set<SpaceMarineAdapter> newItems = new HashSet<>(AnswerHandler.show());

        for (SpaceMarineAdapter newSm : newItems) {
            if (!list.contains(newSm)) {
                isItemsChanged = true;
                break;
            }
        }


        if (isItemsChanged) {
            list = newItems;
            setGridPoints();
            setUserColors();
            updateCircles();
        }

//        items.addAll(list);

        return FXCollections.observableArrayList(newItems);
    }

    private void setGridPoints() {
        for (SpaceMarineAdapter sm : list) {
            GRID_X0 = Math.min(GRID_X0, sm.getX());
            GRID_Y0 = Math.min(GRID_Y0, sm.getY());
            GRID_X1 = Math.max(GRID_X1, sm.getX());
            GRID_Y1 = Math.max(GRID_Y1, sm.getY());
            HEALTH_MAX = Math.max(HEALTH_MAX, sm.getHealth());
        }
        GRID_X0 = (long)Math.floor((double)GRID_X0 / 100) * 100;
        GRID_Y0 = (long)Math.floor((double)GRID_Y0 / 100) * 100;
        GRID_X1 = (long)Math.ceil((double)GRID_X1 / 100) * 100;
        GRID_Y1 = (long)Math.ceil((double)GRID_Y1 / 100) * 100;

        x0Label.setText(String.valueOf(GRID_X0));
        y0Label.setText(String.valueOf(GRID_Y0));
        x1Label.setText(String.valueOf(GRID_X1));
        y1Label.setText(String.valueOf(GRID_Y1));
    }

    private void setUserColors() {
        for (SpaceMarineAdapter sm : list) {
            if (!userColors.containsKey(sm.getOwnerId())) {
                userColors.put(sm.getOwnerId(), Color.color(Math.random(), Math.random(), Math.random()));
            }
        }
    }

    public Stage setupVisualStage(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/gui/visual.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent smRoot = loader.getRoot();
        smRoot.setLayoutX(820);

        Stage visualStage = new Stage();
        visualStage.setTitle("Visual");
        visualStage.setX(100);
        visualStage.setY(100);

        Canvas canvas = new Canvas(820, 760);
        GraphicsContext context = canvas.getGraphicsContext2D();
        drawShapes(context);

        // TODO Это должно быть в отдельном методе, чтобы при обновлении вершин оно тоже менялось

        Label xLabel = new Label("X");
        xLabel.setLayoutX(820);
        xLabel.setLayoutY(730);

        Label yLabel = new Label("Y");
        yLabel.setLayoutX(60);
        yLabel.setLayoutY(5);

        // Draw circles with handling effects


//        Pane overlay = getCircles(loader, width, height, x0, y0, x1, y1);

        innerRoot.getChildren().addAll(canvas, xLabel, yLabel, x0Label, y0Label, x1Label, y1Label, smRoot);

        currentVisualScene = new Scene(innerRoot, 1060, 760);
        visualStage.setScene(currentVisualScene);
        return visualStage;
    }

    private Set<Circle> getCircles() {
        int width = 820;
        int height = 760;
        int x0 = 90;
        int y0 = 30;
        int x1 = width - 30;
        int y1 = height - y0;

        Set<Circle> circles = new HashSet<>();
        MouseGestures mg = new MouseGestures();
        mg.setController(loader.getController());

        for (SpaceMarineAdapter sm : list) {
            double radius = getRadius(sm.getHealth());
            Circle circle = new Circle(radius);
            circle.setFill(userColors.get(sm.getOwnerId()));
            double x = (double)(sm.getX() - GRID_X0) / (GRID_X1 - GRID_X0) * (x1 - x0) + x0;
            double y = height - ((double)(sm.getY() - GRID_Y0) / (GRID_Y1 - GRID_Y0) * (y1 - y0) + y0);
            circle.setCenterX(x);
            circle.setCenterY(y);
            circles.add(circle);

            mg.makeClickable(circle, sm);
        }

        innerRoot.getChildren().removeAll(currentCircles);
        currentCircles.clear();
        currentCircles.addAll(circles);
        innerRoot.getChildren().addAll(currentCircles);

        return circles;
    }

    // TODO реализовать автоматическое появление вершин на плоскость при обновлении их списка
    private void updateCircles() {
        if (innerRoot != null) {
            getCircles();
        }
        isItemsChanged = false;
    }

    private void drawShapes(GraphicsContext gc) {
        int width = 820;
        int height = 760;
        gc.setFill(Color.GRAY);
        gc.setStroke(Color.BLACK);

        // Сетка
        int step = 70;
        int x0 = 90;
        int y0 = 30;
        int x1 = width - 30;
        int y1 = height - y0;
        for (int i = y0; i < y1; i += step) {
            gc.strokeLine(x0, i, x1, i);
        }
        gc.strokeLine(x0, y1, width, y1);
        gc.strokeLine(width, y1, width - 10, y1 - 10);
        gc.strokeLine(width, y1, width - 10, y1 + 10);

        for (int i = x0 + step; i <= x1; i+= step) {
            gc.strokeLine(i, y0, i, y1);
        }
        gc.strokeLine(x0, y1, x0, 5);
        gc.strokeLine(x0, 5, x0 - 10, 15);
        gc.strokeLine(x0, 5, x0 + 10, 15);
    }

    private double getRadius(float health) {
        return 10 * Math.log(health) / Math.log(HEALTH_MAX);
    }
}
