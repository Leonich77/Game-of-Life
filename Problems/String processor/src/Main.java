import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input
    String str;

    private boolean hasLowerCh(String str) {
        boolean ret = false;
        for (Character ch : str.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    @Override
    public void run() {
        while (true) {
            str = scanner.nextLine();
            if (hasLowerCh(str)) {
                System.out.println(str.toUpperCase());
            } else {
                System.out.println("FINISHED");
                break;
            }
        }
    }
}