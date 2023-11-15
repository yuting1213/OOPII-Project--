import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MusicPlay implements Runnable{
    private boolean execute = true;
    private String BGM;
    private boolean stop = false;
    private int pause = 1000;
    
    public MusicPlay(String BGM,boolean stop){
        this.stop = stop;
        this.BGM = BGM;
    }

    public void startMusic(){
        Thread musicThread = new Thread(this);
        musicThread.start();
    }

    public void setStop(Boolean stop){
        this.stop = stop ;
    }
    public boolean getStop(){
        return this.stop ;
    }

    @Override
    public void run(){
        while(execute){
            if(!stop)
            {
                music(BGM);
                doNothing(pause);
            }
            else{
                execute = !execute;           
            }
        }
    }
    public void doNothing(int milliseconds){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public void music(String musicFile){
        File filename = new File(musicFile);
        AudioInputStream audioInputStream = null;
        try{
            audioInputStream = AudioSystem.getAudioInputStream(filename);
        }catch(Exception error){
            error.printStackTrace();
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        int bufferSize = (int) Math.min(audioInputStream.getFrameLength() * format.getFrameSize(),Integer.MAX_VALUE);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format,bufferSize);

        try{
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        }catch(Exception e){
            e.printStackTrace();
        }
        auline.start();
        int nBytesRead = 0;
        byte[] auData = new byte[20];
        try{
            while(nBytesRead != -1){
                if(stop == true)
                auline.stop();
                nBytesRead = audioInputStream.read(auData, 0, auData.length);
                if(nBytesRead >= 0)
                    auline.write(auData,0,nBytesRead);
            }
        }catch(IOException e1){
            e1.printStackTrace();
            return;
        }finally{
            auline.drain();
            auline.close();
        }
    }
}