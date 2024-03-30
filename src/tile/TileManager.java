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
        loadMap("/res/maps/WorldMapAllTree.txt");
    }

    /**
     * Loads tile images from resources.
     */
    public void getTileImage(){


        // GRASS 
        setup(0, "grass00", false); // old light grass empty
        setup(1, "grass01", false); // old light grass
        setup(2, "grass000", false); // new dark grass empty
        setup(3, "grass001", false); // new dark grass

        // ROAD
        setup(4, "road000", false); // new simple road
        setup(5, "road001", false); // new top right corner road 
        setup(6, "road002", false); // new top road 
        setup(7, "road003", false); // new top left corner road 
        setup(8, "road004", false); // new left road 
        setup(9, "road005", false); // new right road 
        setup(10, "road006", false); // new bottom left corner road 
        setup(11, "road007", false); // new bottom road 
        setup(12, "road008", false); // new bottom corner road 
        setup(13, "road009", false); // new bottom right little corner road
        setup(14, "road010", false); // new bottom left little corner road 
        setup(15, "road011", false); // new up right little corner road 
        setup(16, "road012", false); // new left right little corner road 


        // TREE & MUSHROOM
        setup(17, "tree", true); // old light tree (old grass)
        setup(18, "tree000", true); // new light blue tree
        setup(19, "tree001", true); // new dark blue tree
        setup(20, "tree002", true); // new red tree
        setup(21, "tree003", true); // new dead tree
        setup(22, "mushroomtree000", true); // new mushroom tree
 
         // WATER
        setup(23, "water00", true); // old ocean water empty
        setup(24, "water01", true); // old ocean water reflexion
        setup(25, "water02", true); // old ocean water top left corner
        setup(26, "water03", true); // old ocean water top
        setup(27, "water04", true); // old ocean water top right corner
        setup(28, "water05", true); // old ocean water 
        setup(29, "water06", true); // old ocean water
        setup(30, "water07", true); // old ocean water
        setup(31, "water08", true); // old ocean water
        setup(32, "water09", true); // old ocean water
        setup(33, "water10", true); // old ocean water
        setup(34, "water11", true); // old ocean water
        setup(35, "water12", true); // old ocean water
        setup(36, "water13", true); // old ocean water
        setup(37, "water000", true); // new water
        setup(38, "water001", true); // new water
        setup(39, "water002", true); // new water
        setup(40, "water003", true); // new water
        setup(41, "water004", true); // new water
        setup(42, "water005", true); // new water
        setup(43, "water006", true); // new water
        setup(44, "water007", true); // new water
        setup(45, "water008", true); // new water
        setup(46, "water009", true); // new water
        setup(47, "water010", true); // new water
        setup(48, "water011", true); // new water
        setup(49, "water012", true); // new water
        setup(50, "water013", true); // new water

        // EXTERIOR DECORATIONS 
        setup(51, "earth", false); // old earth
        setup(52, "earth000", false); // new earth 
        setup(53, "wall", true); // old wall 
        setup(54, "wall000", true); // new wall no wine
        setup(55, "wall001", true); // new wall blue wine
        setup(56, "wall002", true); // new wall red wine
        setup(57, "wall003", true); // new wall dead wine
        setup(58, "wall004", true); // new wall green wine

        // INTERIOR DECORATIONS
        setup(59, "floor01", false); // old floor
        setup(60, "table01", true); // old table
        setup(61, "hut", true); // old hut (light grass)
        setup(62, "hut000", true); // new hut (new grass)


        // fake
        setup(63, "water000", false); // fake water
        setup(64, "water005", false); // fake water
        setup(65, "wall001", false); // fake wall
        setup(66, "tree001", false); // fake tree

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
            System.out.println("/res/tiles/"+imagePath+".png");;
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
        