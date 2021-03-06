import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public static String delimiter = "!";

    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder(Passwords.KEY).build();
        jda.addEventListener(new commands.Film());
        jda.addEventListener(new commands.Actor());
        jda.addEventListener(new commands.List());
    }
}
