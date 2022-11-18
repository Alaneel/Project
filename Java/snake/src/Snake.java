import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
/**
 * A Snake is made up of one or more SnakeSegments.
 * This class implements GameState interface but only overrides the
 * newGame(). See game state diagram.
 *
 * When the snake moves forward:
 * - The head segment grows by one cell.
 * - If no food is eaten, the tail segment shrink by one cell; otherwise
 *   no change.
 * - No change for the intermediate segments.
 */
public class Snake implements StateTransition {
   // == Define named constants ==
   /** Initial Length of the snake */
   private static final int INIT_LENGTH = 12;
   /** Size of each body cell in pixels */
   public static final int CELL_SIZE = GameMain.CELL_SIZE;
   public static final Color COLOR_HEAD = Color.RED;  // color for the snake's head
   public static final Color COLOR_BODY = Color.PINK; // color for the snake's body

   // == Define the snake properties (package-visible) ==
   // The snake segments that forms the snake
   List<SnakeSegment> segments = new ArrayList<SnakeSegment>();

   // The current moving direction of this snake
   Move direction;

   /** Default Constructor */
   public Snake() { }

   /**
    * Regenerate the snake for a new game.
    */
   @Override
   public void newGame() {
      segments.clear();
      // Create a snake at the center of the pit, facing up.
      int headX = GameMain.COLS / 2;
      int headY = GameMain.ROWS / 2;
      int length = INIT_LENGTH;
      direction = Move.UP;
      segments.add(new SnakeSegment(headX, headY, length, direction));
   }

   /**
    * Change the direction of the snake, but no 180 degree turn allowed.
    */
   public void changeDirection(Move newDir) {
      // No 180 degree turn
      if ((newDir != direction) &&
          ((newDir == Move.UP && direction != Move.DOWN)
             || (newDir == Move.DOWN && direction != Move.UP)
             || (newDir == Move.LEFT && direction != Move.RIGHT)
             || (newDir == Move.RIGHT && direction != Move.LEFT))) {

         SnakeSegment headSegment = segments.get(0); // get the head segment
         int x = headSegment.headX;
         int y = headSegment.headY;
         // Insert a new segment with zero length as the new head segment
         segments.add(0, new SnakeSegment(x, y, 0, newDir));
         direction = newDir;
      }
   }

   /**
    * Growing the head segment by one cell.
    */
   public void grow() {
      // Grow the head segment
      SnakeSegment headSegment = segments.get(0); // "head" segment
      headSegment.grow();
   }

   /**
    * Shrink the tail segment by one cell (not eaten food)
    */
   public void shrink() {
      SnakeSegment tailSegment;
      tailSegment = segments.get(segments.size() - 1);
      tailSegment.shrink();
      if (tailSegment.length == 0) {
         segments.remove(tailSegment);
      }
   }

   /**
    * Get the X coordinate of the cell that contains the head of this snake segment.
    *
    * @return x of the snake head
    */
   public int getHeadX() {
      return segments.get(0).headX;
   }

   /**
    * Get the Y coordinate of the cell that contains the head of this snake segment.
    *
    * @return y of the snake head
    */
   public int getHeadY() {
      return segments.get(0).headY;
   }

   /**
    * Get the length of this snake by adding up all the segments.
    *
    * @return the length of this snake
    */
   public int getLength() {
      int length = 0;
      for (SnakeSegment segment : segments) {
         length += segment.length;
      }
      return length;
   }

   /**
    * Check if this snake contains the given (x, y) cell. Used in collision
    * detection
    *
    * @return true if this snake contains the given (x, y)
    */
   public boolean contains(int x, int y) {
      for (int i = 0; i < segments.size(); i++) {
         SnakeSegment segment = segments.get(i);
         if (segment.contains(x, y)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Check if the snake eats itself.
    *
    * @return true if the snake eats itself
    */
   public boolean eatItself() {
      int headX = getHeadX();
      int headY = getHeadY();
      // eat itself if the (headX, headY) hits its body segment (4th onwards)
      for (int i = 3; i < segments.size(); i++) {
         SnakeSegment segment = segments.get(i);
         if (segment.contains(headX, headY)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Paint itself via the Graphics context.
    * The JPanel's repaint() in GameMain class callbacks paintComponent(Graphics)
    * This snake.paint(Graphics) shall be placed in paintComponent(Graphics).

    * @param g - the drawing Graphics object
    */
   public void paint(Graphics g) {
      g.setColor(COLOR_BODY);
      for (int i = 0; i < segments.size(); i++) {
         segments.get(i).paint(g); // draw all the segments
      }
      // Paint over the head again using a different head color and bigger
      int offset = 2;
      if (segments.size() > 0) {
         g.setColor(COLOR_HEAD);
         g.fill3DRect(getHeadX()*CELL_SIZE-offset/2, getHeadY()*CELL_SIZE-offset/2,
               CELL_SIZE-1+offset, CELL_SIZE-1+offset, true);
      }
   }
}