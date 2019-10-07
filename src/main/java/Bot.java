import events.HelloWorld;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    public static void main(String[] args) {

        JDA jda = null;
        try {
            jda = new JDABuilder(Passwords.KEY).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert jda != null;
        jda.addEventListener(new HelloWorld());


    }
}
