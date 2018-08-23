package semulajadi.v011;

/**
 * Created by Ryan on 28/03/2018.
 */
import java.util.Random;

public class image {

    private static final Random Random = new Random();

    public static int getRandomDrawable() {
        switch (Random.nextInt(2)) {
            default:
            case 0:
                return R.drawable.bg_tab;
            case 1:
                return R.drawable.bg_bm;

        }
    }

}
