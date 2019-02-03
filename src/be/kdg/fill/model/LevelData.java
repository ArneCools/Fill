package be.kdg.fill.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelData {
    static private List<Level> easyLevels;
    static private List<Level> mediumLevels;
    static private List<Level> hardLevels;
    static private boolean isLevelsLoaded = false;

    static private int playerProgressEasy = 0;
    static private int playerProgressMedium = 0;
    static private int playerProgressHard = 0;
    static private int hintsAvailable = 0;

    static final String FILL_PATH = "Fill";
    static final String LEVELS_PATH = "levels";
    static final String DEFAULT_LEVELS_PATH = "be/kdg/fill/model/levels";
    static final String EASY_PATH = "easy";
    static final String MEDIUM_PATH = "medium";
    static final String HARD_PATH = "hard";
    static final String PLAYER_PATH = "saveGame";
    static final String PLAYER_FILE_PATH = "saveGame.fill.stats";

    static private String easyPath;
    static private String mediumPath;
    static private String hardPath;
    static private String playerPath;


    static public void loadLevels() {
        easyLevels = new ArrayList<Level>();
        mediumLevels = new ArrayList<Level>();
        hardLevels = new ArrayList<Level>();

        //create folders if necessary

        String levelsPath = System.getProperty("user.home") + File.separator + FILL_PATH;

        mkdir(levelsPath);

        playerPath = levelsPath + File.separator + PLAYER_PATH;

        levelsPath += File.separator + LEVELS_PATH;

        mkdir(levelsPath);

        easyPath = levelsPath + File.separator + EASY_PATH;
        mediumPath = levelsPath + File.separator + MEDIUM_PATH;
        hardPath = levelsPath + File.separator + HARD_PATH;
        mkdir(easyPath);
        mkdir(mediumPath);
        mkdir(hardPath);
        mkdir(playerPath);

        loadDefaultLevels(DEFAULT_LEVELS_PATH);
        loadLevels(levelsPath);
        System.out.println("easyLevels = " + easyLevels.size());
        System.out.println("mediumLevels = " + mediumLevels.size());
        System.out.println("hardLevels = " + hardLevels.size());

        Collections.sort(easyLevels);
        Collections.sort(mediumLevels);
        Collections.sort(hardLevels);
        loadPlayerData();
        isLevelsLoaded = true;


    }

    static private void mkdir(String path) {
        if (!Files.exists(Paths.get(path))) {
            try {
                Files.createDirectory(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static private void loadDefaultLevels(String path) {
        //Load default levels
        File levelFolder = new File(path);

        File[] fileList = levelFolder.listFiles();
        //System.out.println(LevelData.class.getResourceAsStream("/be/kdg/fill/files/levels/easy/level1.fill"));
        Difficulty difficultyToLoad = Difficulty.easy;
        int levelToLoad = 0;
        System.out.println(difficultyToLoad);
        for (int i = 0; i < 30; i++) {
            levelToLoad++;
            if (levelToLoad > 10) {
                switch (difficultyToLoad) {
                    case easy:
                        difficultyToLoad = Difficulty.medium;
                        break;
                    case medium:
                        difficultyToLoad = Difficulty.hard;
                        break;
                    case hard:
                        break;
                }
                levelToLoad = 1;
            }
            try (DataInputStream is = new DataInputStream(
                    new BufferedInputStream(
                            LevelData.class.getResourceAsStream("/be/kdg/fill/files/levels/" + difficultyToLoad.toString() + "/level" + levelToLoad + ".fill")))) {
                loadLevel(is);
            } catch (FileNotFoundException e) {
                throw new FillException("An exception occured when loading the default levels: " + e.getMessage());
            } catch (IOException e) {
                throw new FillException("An exception occured when loading the default levels: " + e.getMessage());
            }
        }
    }

    public static void loadLevels(String path) {
        //search for all levels in 'levels' folder recursively
        File levelFolder = new File(path);

        File[] fileList = levelFolder.listFiles();

        for (File file :
                fileList) {
            if (file.isFile()) {
                if (!file.getPath().contains(".fill")) {
                    continue;
                }


                try (DataInputStream is = new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)))) {
                    loadLevel(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                   FileInputStream inputStream = new FileInputStream(file);
                   ObjectInputStream objectIn = new ObjectInputStream(inputStream);
                    level = (Level)objectIn.readObject();
                    inputStream.close();
                    objectIn.close();
                    */
            } else if (file.isDirectory()) {
                loadLevels(file.getPath());
            }
        }
    }

    public static void loadLevel(DataInputStream is) throws IOException {
        Level level = null;
        int levelNumber = is.readInt();
        int difficulty = is.readInt();
        int rows = is.readInt();
        int columns = is.readInt();
        Block[][] grid = new Block[columns][rows];
        List<Block> SolutionPath = new ArrayList<Block>();

        //Convert grid string to object
        String[] gridColumns = is.readUTF().split(";");
        for (String stringColumn :
                gridColumns) {
            String[] gridRows = stringColumn.split(":");

        }

        for (int i = 0; i < gridColumns.length; i++) {
            String[] gridRows = gridColumns[i].split(":");
            for (int j = 0; j < gridRows.length; j++) {
                Block block = new Block(i, j);
                //System.out.println(Integer.parseInt(gridRows[j]));
                block.setBlockState(BlockState.values()[Integer.parseInt(gridRows[j])]);
                grid[i][j] = block;
            }
        }

        Grid gridObject = new Grid(grid);


        //convert path string to object
        String[] pathArrayString = is.readUTF().split(":");
        for (int i = 0; i < pathArrayString.length; i++) {
            String[] pathBlockString = pathArrayString[i].split(",");
            int column = Integer.parseInt(pathBlockString[0]);
            int row = Integer.parseInt(pathBlockString[1]);
            SolutionPath.add(grid[column][row]);
        }
        gridObject.setSolutionPath(SolutionPath);

        level = new Level(columns, rows, Difficulty.values()[difficulty], levelNumber, gridObject);

        switch (level.getDifficulty()) {
            case easy:
                easyLevels.add(level);
                break;
            case medium:
                mediumLevels.add(level);
                break;
            case hard:
                hardLevels.add(level);
                break;
        }

    }

    public static List<Level> getLevels(Difficulty difficulty) {
        if (!isLevelsLoaded) {
            loadLevels();
        }
        switch (difficulty) {
            case easy:
                return easyLevels;
            case medium:
                return mediumLevels;
            case hard:
                return hardLevels;
            default:
                throw new FillException("Error: wrong difficulty");
        }
    }

    public static void saveLevel(Level level) throws IOException {
        if (!isLevelsLoaded) {
            loadLevels();
        }
        String level_path = "";

        switch (level.getDifficulty()) {
            case easy:
                level_path = easyPath;
                easyLevels.add(level);
                break;
            case medium:
                level_path = mediumPath;
                mediumLevels.add(level);
                break;
            case hard:
                level_path = hardPath;
                hardLevels.add(level);
                break;
        }

        level_path += "/level" + level.getLevelNumber() + ".fill";

        int levelNumber = level.getLevelNumber();
        int difficulty = level.getDifficulty().ordinal();
        int rows = level.getGrid().getBlocks()[0].length;
        int columns = level.getGrid().getBlocks().length;
        String gridString = "";
        String pathString = "";

        //make gridString
        for (int i = 0; i < level.getGrid().getBlocks().length; i++) {
            for (int j = 0; j < level.getGrid().getBlocks()[0].length; j++) {
                gridString += level.getGrid().getBlocks()[i][j].getBlockState().ordinal();
                if (j + 1 < level.getGrid().getBlocks()[0].length) gridString += ":";
            }
            if (i + 1 < level.getGrid().getBlocks().length) gridString += ";";
        }

        //make pathString
        for (int i = 0; i < level.getGrid().getSolutionPath().size(); i++) {
            pathString += level.getGrid().getSolutionPath().get(i).getColumn();
            pathString += ",";
            pathString += level.getGrid().getSolutionPath().get(i).getRow();
            if (i + 1 < level.getGrid().getSolutionPath().size()) pathString += ":";
        }


        try (DataOutputStream os = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(level_path)))) {
            os.writeInt(levelNumber);
            os.writeInt(difficulty);
            os.writeInt(rows);
            os.writeInt(columns);
            os.writeUTF(gridString);
            os.writeUTF(pathString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*
        FileOutputStream outputStream = new FileOutputStream(level_path);
        ObjectOutputStream objectOut =  new ObjectOutputStream(outputStream);
        objectOut.writeObject(level);
        objectOut.close();
        outputStream.close();
        */


    }

    public static int getPlayerProgress(Difficulty difficulty) {
        switch (difficulty) {
            case easy:
                return playerProgressEasy;
            case medium:
                return playerProgressMedium;
            case hard:
                return playerProgressHard;
        }
        return playerProgressHard;
    }

    public static void savePlayerData() {

        try (DataOutputStream os = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(playerPath + File.separator + PLAYER_FILE_PATH)))) {
            os.writeInt(playerProgressEasy);
            os.writeInt(playerProgressMedium);
            os.writeInt(playerProgressHard);
            os.writeInt(hintsAvailable);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadPlayerData() {
        if (!Files.exists(Paths.get(playerPath + File.separator + PLAYER_FILE_PATH))) {
            hintsAvailable = 10;
            return;
        }
        try (DataInputStream is = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(playerPath + File.separator + PLAYER_FILE_PATH)))) {
            playerProgressEasy = is.readInt();
            playerProgressMedium = is.readInt();
            playerProgressHard = is.readInt();
            hintsAvailable = is.readInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void levelCompleted(Difficulty difficulty) {
        hintsAvailable++;
        switch (difficulty) {
            case easy:
                playerProgressEasy++;
                break;
            case medium:
                playerProgressMedium++;
                break;
            case hard:
                playerProgressHard++;
                break;
        }
    }

    public static void resetPlayerProgress() {
        playerProgressEasy = 0;
        playerProgressMedium = 0;
        playerProgressHard = 0;
        hintsAvailable = 10;
        savePlayerData();
    }

    public static int getHintsAvailable() {
        return hintsAvailable;
    }

    public static void useHint() {
        hintsAvailable--;
        savePlayerData();
    }
}
