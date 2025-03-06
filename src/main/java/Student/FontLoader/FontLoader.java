package Student.FontLoader;

import javafx.scene.text.Font;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FontLoader {

    private final List<Font> loadedFonts = new ArrayList<>();

    public void loadFonts() {
        try {
            File fontsFolder = new File(getClass().getResource("/fonts").toURI());

            for (File fontFile : fontsFolder.listFiles()) {
                if (fontFile.isFile() && fontFile.getName().endsWith(".ttf")) {
                    InputStream fontStream = getClass().getResourceAsStream("/fonts/" + fontFile.getName());
                    Font font = Font.loadFont(fontStream, 20);

                    if (font != null) {
                        loadedFonts.add(font);
                        System.out.println("Loaded font: " + fontFile.getName());
                    } else {
                        System.err.println("Failed to load font: " + fontFile.getName());
                    }
                }
            }
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("Error loading fonts: " + e.getMessage());
        }
    }

    public List<Font> getLoadedFonts() {
        return loadedFonts;
    }
}
