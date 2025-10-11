public class SnakeSkin {
    private String horizontal_body, vertical_body;    
    private String body_curve_lu, body_curve_ru, body_curve_ld, body_curve_rd;
    private String body_end_up,body_end_down,body_end_left,body_end_right;
    private String head_up,head_down,head_left,head_right;
    private String dead_head_up,dead_head_down,dead_head_left,dead_head_right;




    SnakeSkin(
        String horizontal_body,String vertical_body,
        String body_curve_lu, String body_curve_ru,String body_curve_ld,String body_curve_rd,
        String body_end_up, String body_end_down, String body_end_left, String body_end_right,
        String head_up,String head_down, String head_left, String head_right,
        String dead_head_up,String dead_head_down, String dead_head_left, String dead_head_right
    ){
        this.horizontal_body = horizontal_body;
        this.vertical_body = vertical_body;
        this.body_curve_lu = body_curve_lu;
        this.body_curve_ru = body_curve_ru;
        this.body_curve_ld = body_curve_ld;
        this.body_curve_rd = body_curve_rd;
        this.body_end_up = body_end_up;
        this.body_end_down = body_end_down;
        this.body_end_left = body_end_left;
        this.body_end_right = body_end_right;
        this.head_up = head_up;
        this.head_down = head_down;
        this.head_left = head_left;
        this.head_right = head_right;
        this.dead_head_up = dead_head_up;
        this.dead_head_down = dead_head_down;
        this.dead_head_left = dead_head_left;
        this.dead_head_right = dead_head_right;
    }

    public String getBody_curve_ld() {
        return body_curve_ld;
    }
    public String getBody_curve_lu() {
        return body_curve_lu;
    }
    public String getBody_curve_rd() {
        return body_curve_rd;
    }
    public String getBody_curve_ru() {
        return body_curve_ru;
    }
    public String getBody_end_down() {
        return body_end_down;
    }
    public String getBody_end_left() {
        return body_end_left;
    }
    public String getBody_end_right() {
        return body_end_right;
    }
    public String getBody_end_up() {
        return body_end_up;
    }
    public String getDead_head_down() {
        return dead_head_down;
    }
    public String getDead_head_left() {
        return dead_head_left;
    }
    public String getDead_head_right() {
        return dead_head_right;
    }
    public String getDead_head_up() {
        return dead_head_up;
    }
    public String getHead_down() {
        return head_down;
    }
    public String getHead_left() {
        return head_left;
    }
    public String getHead_right() {
        return head_right;
    }
    public String getHead_up() {
        return head_up;
    }
    public String getHorizontal_body() {
        return horizontal_body;
    }
    public String getVertical_body() {
        return vertical_body;
    }
    


}


