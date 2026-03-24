package dev.gaspard.ffdc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.gaspard.ffdc.GameConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads and renders a text-based tile map.
 *
 * <p>The map file contains {@link GameConstants#WORLD_COLS} x
 * {@link GameConstants#WORLD_ROWS} space-separated tile IDs, one row per line,
 * read top-to-bottom. Because LibGDX uses a bottom-up Y axis the map is stored
 * internally as {@code mapData[col][row]} where row 0 is the bottom of the
 * world.  Tile textures are lazy-loaded and cached on first use.
 */
public final class GameMap {

    private static final String TAG = "GameMap";

    /** Tile data indexed as [col][row], row 0 = world bottom. */
    private final int[][] mapData;

    /** Cache of already-loaded tile textures, keyed by tile ID. */
    private final Map<Integer, Texture> tileCache = new HashMap<>();

    /**
     * Loads a map from the given internal asset path.
     *
     * @param mapFilePath internal path to the map text file
     *                    (e.g. {@code "maps/WorldMapAllTree.txt"})
     */
    public GameMap(String mapFilePath) {
        mapData = new int[GameConstants.WORLD_COLS][GameConstants.WORLD_ROWS];
        loadMap(mapFilePath);
    }

    /** Parses the map file and populates {@link #mapData}. */
    private void loadMap(String path) {
        FileHandle file = Gdx.files.internal(path);
        String[] lines = file.readString().trim().split("\\r?\\n");

        int totalRows = Math.min(lines.length, GameConstants.WORLD_ROWS);
        for (int fileRow = 0; fileRow < totalRows; fileRow++) {
            // File row 0 is the top of the world; LibGDX row 0 is the bottom.
            int worldRow = GameConstants.WORLD_ROWS - 1 - fileRow;
            String[] tokens = lines[fileRow].trim().split("\\s+");
            int totalCols = Math.min(tokens.length, GameConstants.WORLD_COLS);
            for (int col = 0; col < totalCols; col++) {
                mapData[col][worldRow] = Integer.parseInt(tokens[col]);
            }
        }
        Gdx.app.log(TAG, "Map loaded: " + path);
    }

    /**
     * Returns the tile ID at the given map coordinates.
     *
     * @param col column index (0 = left)
     * @param row row index (0 = bottom of the world)
     * @return tile ID, or 0 if out of bounds
     */
    public int getTile(int col, int row) {
        if (col < 0 || col >= GameConstants.WORLD_COLS
                || row < 0 || row >= GameConstants.WORLD_ROWS) {
            return 0;
        }
        return mapData[col][row];
    }

    /**
     * Returns the underlying tile data array indexed as {@code [col][row]}.
     *
     * @return reference to the internal map array (do not modify)
     */
    public int[][] getMapData() {
        return mapData;
    }

    /**
     * Draws only the tiles that intersect the visible viewport, applying
     * frustum culling to avoid drawing off-screen tiles.
     *
     * <p>The camera position ({@code cameraX}, {@code cameraY}) is the
     * world-pixel coordinate of the viewport's bottom-left corner.
     *
     * @param batch     the sprite batch to draw into (must be between begin/end)
     * @param cameraX   world X of the viewport's left edge
     * @param cameraY   world Y of the viewport's bottom edge
     * @param viewportW viewport width in pixels
     * @param viewportH viewport height in pixels
     */
    public void render(SpriteBatch batch, float cameraX, float cameraY,
                       int viewportW, int viewportH) {
        int ts = GameConstants.TILE_SIZE;

        int colStart = Math.max(0, (int) (cameraX / ts));
        int colEnd   = Math.min(GameConstants.WORLD_COLS - 1,
                                (int) ((cameraX + viewportW) / ts));
        int rowStart = Math.max(0, (int) (cameraY / ts));
        int rowEnd   = Math.min(GameConstants.WORLD_ROWS - 1,
                                (int) ((cameraY + viewportH) / ts));

        for (int col = colStart; col <= colEnd; col++) {
            for (int row = rowStart; row <= rowEnd; row++) {
                int tileId = mapData[col][row];
                Texture tex = getOrLoadTile(tileId);
                if (tex != null) {
                    float screenX = col * ts - cameraX;
                    float screenY = row * ts - cameraY;
                    batch.draw(tex, screenX, screenY, ts, ts);
                }
            }
        }
    }

    /**
     * Returns (and caches) the texture for a tile ID, resolving the asset
     * path via a deterministic naming convention.
     *
     * @param tileId the tile identifier
     * @return the loaded texture, or {@code null} if no sprite exists for it
     */
    private Texture getOrLoadTile(int tileId) {
        if (tileCache.containsKey(tileId)) {
            return tileCache.get(tileId);
        }
        String path = resolveTilePath(tileId);
        if (path == null) {
            tileCache.put(tileId, null);
            return null;
        }
        try {
            Texture tex = new Texture(Gdx.files.internal(path));
            tileCache.put(tileId, tex);
            return tex;
        } catch (Exception e) {
            Gdx.app.error(TAG, "Cannot load tile " + tileId + " from " + path, e);
            tileCache.put(tileId, null);
            return null;
        }
    }

    /**
     * Maps a tile ID to an asset path inside {@code sprites/tiles/}.
     *
     * <p>The naming convention mirrors the existing asset folder structure.
     * Grass (0–16), trees (17–22), water (23–50), walls (51–58) and roads
     * are mapped to their respective files.
     *
     * @param tileId the tile identifier
     * @return internal asset path, or {@code null} if unknown
     */
    private String resolveTilePath(int tileId) {
        return switch (tileId) {
            case 0  -> "sprites/tiles/grass00.png";
            case 1  -> "sprites/tiles/grass01.png";
            case 2  -> "sprites/tiles/earth.png";
            case 3  -> "sprites/tiles/road00.png";
            case 4  -> "sprites/tiles/road01.png";
            case 5  -> "sprites/tiles/road02.png";
            case 6  -> "sprites/tiles/road03.png";
            case 7  -> "sprites/tiles/road04.png";
            case 8  -> "sprites/tiles/road05.png";
            case 9  -> "sprites/tiles/road06.png";
            case 10 -> "sprites/tiles/road07.png";
            case 11 -> "sprites/tiles/road08.png";
            case 12 -> "sprites/tiles/road09.png";
            case 13 -> "sprites/tiles/road10.png";
            case 14 -> "sprites/tiles/road11.png";
            case 15 -> "sprites/tiles/road12.png";
            case 16 -> "sprites/tiles/floor01.png";
            // Trees (solid 17–22)
            case 17 -> "sprites/tiles/tree.png";
            case 18 -> "sprites/tiles/tree000.png";
            case 19 -> "sprites/tiles/tree001.png";
            case 20 -> "sprites/tiles/tree002.png";
            case 21 -> "sprites/tiles/tree003.png";
            case 22 -> "sprites/tiles/mushroomtree000.png";
            // Water (solid 23–50) – map to available water files cycling through them
            case 23 -> "sprites/tiles/water00.png";
            case 24 -> "sprites/tiles/water01.png";
            case 25 -> "sprites/tiles/water02.png";
            case 26 -> "sprites/tiles/water03.png";
            case 27 -> "sprites/tiles/water04.png";
            case 28 -> "sprites/tiles/water05.png";
            case 29 -> "sprites/tiles/water06.png";
            case 30 -> "sprites/tiles/water07.png";
            case 31 -> "sprites/tiles/water08.png";
            case 32 -> "sprites/tiles/water09.png";
            case 33 -> "sprites/tiles/water10.png";
            case 34 -> "sprites/tiles/water11.png";
            case 35 -> "sprites/tiles/water12.png";
            case 36 -> "sprites/tiles/water13.png";
            case 37 -> "sprites/tiles/water00.png";
            case 38 -> "sprites/tiles/water01.png";
            // Walls (solid 51–58)
            case 51 -> "sprites/tiles/wall.png";
            case 52 -> "sprites/tiles/wall000.png";
            case 53 -> "sprites/tiles/wall001.png";
            case 54 -> "sprites/tiles/wall002.png";
            case 55 -> "sprites/tiles/wall003.png";
            case 56 -> "sprites/tiles/wall004.png";
            case 57 -> "sprites/tiles/hut.png";
            case 58 -> "sprites/tiles/hut000.png";
            // Extended roads / other tiles
            case 63 -> "sprites/tiles/grass00.png";
            default -> "sprites/tiles/grass00.png";
        };
    }

    /**
     * Disposes all cached tile textures.
     * Must be called when the map is no longer needed.
     */
    public void dispose() {
        for (Texture tex : tileCache.values()) {
            if (tex != null) tex.dispose();
        }
        tileCache.clear();
        Gdx.app.log(TAG, "GameMap disposed");
    }
}
