package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

/**
 * Manages tiles in the game world, including loading tile images and maps.
 */
public class TileManager {
    
    /** Reference to the game panel. */
    GamePanel gp;
    
    /** Array of tiles. */
    public Tile[] tile;
    
    /** 2D array representing the map of tile numbers. */
    public int mapTileNum[][];
    
    /**
     * Constructs a TileManager with a reference to the game panel.
     * @param gp The game panel.
     */
    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[100]; // number of tile we need
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldCol]; // map
        getTileImage();
        loadMap("/res/maps/WorldMap4.txt");
    }

    /**
     * Loads tile images from resources.
     */
    public void getTileImage(){

        setup(0, "grass001", false); // simple grass
        setup(1, "grass001", false); // simple grass
        setup(2, "grass001", false); // simple grass
        setup(3, "grass001", false); // simple grass
        setup(4, "grass001", false); // simple grass
        setup(5, "grass001", false); // simple grass
        setup(6, "grass001", false); // simple grass
        setup(7, "grass001", false); // simple grass
        setup(8, "grass001", false); // simple grass
        setup(9, "grass001", false); // simple grass     

        setup(10, "grass001", false); // simple grass
        setup(11, "grass000", false); // grass

        setup(12, "water00", true); // water
        setup(13, "water01", true); // water
        setup(14, "water02", true); // water
        setup(15, "water03", true); // water
        setup(16, "water04", true); // water
        setup(17, "water05", true); // water
        setup(18, "water06", true); // water
        setup(19, "water07", true); // water
        setup(20, "water08", true); // water
        setup(21, "water09", true); // water
        setup(22, "water10", true); // water
        setup(23, "water11", true); // water
        setup(24, "water12", true); // water
        setup(25, "water13", true); // water
        
        
        setup(26, "road000", false); // road
        setup(27, "road001", false); // road
        setup(28, "road002", false); // road
        setup(29, "road003", false); // road
        setup(30, "road004", false); // road
        setup(31, "road005", false); // road
        setup(32, "road006", false); // road
        setup(33, "road007", false); // road
        setup(34, "road008", false); // road
        setup(35, "road009", false); // road
        setup(36, "road010", false); // road
        setup(37, "road011", false); // road
        setup(38, "road012", false); // road

        setup(39, "earth", false); // earth

        setup(40, "wall000", true); // wall

        setup(41, "tree000", true); // tree

        setup(42, "floor01", false); // floor

        setup(43, "table01", true); // table

        setup(44, "hut000", true); // hut

        setup(45, "water00", false); // fake water

        setup(46, "water05", false); // fake water

        setup(47, "wall001", false); // fake wall

        setup(48, "tree001", false); // fake tree
    }

    /**
     * Sets up a tile with the given index, image path, and collision property.
     * @param index The index of the tile.
     * @param imagePath The path to the tile image.
     * @param collision Whether the tile has collision properties.
     */
    public void setup(int index, String imagePath, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try {
                tile[index] = new Tile();
                tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+imagePath+".png"));
                tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
                tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map from the specified file path.
     * @param mapPath The path to the map file.
     */
    public void loadMap(String mapPath){
        try {

            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;

                    
                }
                if (col == gp.maxWorldCol) {
                    col = 0 ;
                    row++;
                }
            }   

            br.close(); // close the bufferedReader

        } catch (Exception e) {

        }
    }

    /**
     * Draws the tiles in the game world.
     * @param g2 The Graphics2D object to draw on.
     */
    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (   worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
                && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) { // create a boundary to draw only the tiles around the player
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null) ;
                }
                worldCol++;
        
                if (worldCol == gp.maxWorldCol) {
                    worldCol = 0;
                    worldRow++;
                }
            }
        }
    }
        