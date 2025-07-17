package rendering;

import java.awt.Graphics;
import java.util.List;

public class Renderer {
    public void renderAll(Graphics g, List<Renderable> renderables) {
        for (Renderable renderable : renderables) {
            renderable.render(g);  // Render each object
        }
    }
}
