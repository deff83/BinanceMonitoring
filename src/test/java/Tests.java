import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Tests {
    @Test
    public void testIsEmpty() {
        List<Integer> massiv = new ArrayList<>();
        String[] massiv_str = {"1H", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "11H", "12H", "13H", "14H", "15H", "16H", "17H", "18H", "19H", "20H", "21H", "22H", "23H", "1D", "2D", "3D", "4D", "5D"};

        for (int i_m=0; i_m<massiv_str.length; i_m++){
            String mas_t = massiv_str[i_m];
            int mas_ch = Integer.parseInt(mas_t.substring(0, mas_t.length()-1));
            switch (mas_t.substring((mas_t.length()-1), mas_t.length())){
                case "H":
                    mas_ch = mas_ch*1;
                    break;
                case "D":
                    mas_ch = mas_ch*24;
                    break;
            };
            massiv.add(mas_ch);
        }
        System.out.println(massiv.toString());
    }
}
