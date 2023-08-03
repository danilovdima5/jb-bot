package kz.jussan.bot.service;

public class ClimateControlScheduler extends Thread {

    ClimateControlService service = new ClimateControlServiceImpl();

    private void process(){

    }

    @Override
    public void start() {
        super.start();
        while (true) {
            process();
            try {
                sleep(60 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
