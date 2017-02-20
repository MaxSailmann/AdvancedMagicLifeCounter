package de.sailmann.max.advancedmagiclifecounter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

/**
 * Created by Admin on 09.01.2017.
 */

public class UndoBuffer {

    private ArrayList<UndoBufferData> bufferData;
    private int index;
    private int element;
    private Context context;

    public UndoBuffer(Context context) {
        bufferData = new ArrayList<>();
        index = 0;
        this.context = context;
    }

    public int getIndex() {
        return this.index;
    }

    public ArrayList<UndoBufferData> getBufferData() {
        return this.bufferData;
    }

    public void add(int playerNumber, String playerName, int lifeCounterOld, int lifeCounterNew, int poisonCounterOld, int poisonCounterNew, int energyCounterOld, int energyCounterNew) {

        if (lifeCounterNew != lifeCounterOld || poisonCounterNew != poisonCounterOld || energyCounterNew != energyCounterOld) {
            for (int i = bufferData.size(); i > 0; i--) {
                if (i > index) {
                    bufferData.remove(i - 1);
                }
            }
            UndoBufferData data = new UndoBufferData(playerNumber, playerName, lifeCounterOld, lifeCounterNew, poisonCounterOld, poisonCounterNew, energyCounterOld, energyCounterNew);
            bufferData.add(data);
            index++;
            if (index > 10) {
                index = 10;
                ArrayList<UndoBufferData> buffer;
                buffer = bufferData;
                bufferData = new ArrayList<>();

                for (int i = 0; i < (buffer.size() - 1); i++) {

                    bufferData.add(buffer.get(i + 1));
                }
            }
        }
    }

    public ArrayList<Player> undo(ArrayList<Player> players) {
        if (index == 0) {
            Toast toast = makeText(context, context.getResources().getString(R.string.canNotGoBackwards), Toast.LENGTH_SHORT);
            toast.show();
            return players;

        } else {
            element = bufferData.get(index - 1).getPlayerNumber();
            players.get(element).setLifePoints(bufferData.get(index - 1).getLifeCounterOld());
            players.get(element).setPoisonCounter(bufferData.get(index - 1).getPoisonCounterOld());
            players.get(element).setEnergyCounter(bufferData.get(index - 1).getEnergyCounterOld());

            String toastString = "";

            if (bufferData.get(index - 1).getLifeCounterOld() != bufferData.get(index - 1).getLifeCounterNew()) {
                toastString = players.get(element).getName() + ": " + context.getResources().getString(R.string.lifePoints) + " " + bufferData.get(index - 1).getLifeCounterNew() + " -> " + bufferData.get(index - 1).getLifeCounterOld()+ " (" + (index-1) + "/" + bufferData.size() + ")";
            } else if (bufferData.get(index - 1).getPoisonCounterNew() != bufferData.get(index - 1).getPoisonCounterOld()) {
                toastString = players.get(element).getName() + ": " + context.getResources().getString(R.string.poisonCounter) + " " + bufferData.get(index - 1).getPoisonCounterNew() + " -> " + bufferData.get(index - 1).getPoisonCounterOld() + " (" + (index-1) + "/" + bufferData.size() + ")";

            } else if (bufferData.get(index - 1).getEnergyCounterNew() != bufferData.get(index - 1).getEnergyCounterOld()) {
                toastString = players.get(element).getName() + ": " + context.getResources().getString(R.string.energyCounter) + " " + bufferData.get(index - 1).getEnergyCounterNew() + " -> " + bufferData.get(index - 1).getEnergyCounterOld() + " (" + (index-1) + "/" + bufferData.size() + ")";

            }
            Toast toast = makeText(context, toastString, Toast.LENGTH_SHORT);
            toast.show();
            index--;
            return players;
        }
    }

    public ArrayList<Player> redo(ArrayList<Player> players) {
        if (index < bufferData.size()) {
            element = bufferData.get(index).getPlayerNumber();
            players.get(element).setLifePoints(bufferData.get(index).getLifeCounterNew());
            players.get(element).setPoisonCounter(bufferData.get(index).getPoisonCounterNew());
            players.get(element).setEnergyCounter(bufferData.get(index).getEnergyCounterNew());


            String toastString = "";

            if (bufferData.get(index).getLifeCounterOld() != bufferData.get(index).getLifeCounterNew()) {
                toastString = players.get(element).getName() + ": " + context.getResources().getString(R.string.lifePoints) + " " + bufferData.get(index).getLifeCounterOld() + " -> " + bufferData.get(index).getLifeCounterNew()+ " (" + (index+1) + "/" + bufferData.size() + ")";
            } else if (bufferData.get(index).getPoisonCounterNew() != bufferData.get(index).getPoisonCounterOld()) {
                toastString = players.get(element).getName() + ": " + context.getResources().getString(R.string.poisonCounter) + " " + bufferData.get(index).getPoisonCounterOld() + " -> " + bufferData.get(index).getPoisonCounterNew()+ " (" + (index+1) + "/" + bufferData.size() + ")";

            } else if (bufferData.get(index).getEnergyCounterNew() != bufferData.get(index).getEnergyCounterOld()) {
                toastString = players.get(element).getName() + ": " + context.getResources().getString(R.string.energyCounter) + " " + bufferData.get(index).getEnergyCounterOld() + " -> " + bufferData.get(index).getEnergyCounterNew()+ " (" + (index+1) + "/" + bufferData.size() + ")";

            }
            Toast toast = makeText(context, toastString, Toast.LENGTH_SHORT);
            toast.show();


            index++;
            return players;
        } else {
            Toast toast = makeText(context, context.getResources().getString(R.string.canNotGoForewards), Toast.LENGTH_SHORT);
            toast.show();
            return players;
        }
    }

    public String getText() {
        StringBuffer stringBuffer = new StringBuffer();

        int counter = 1;

        for (UndoBufferData data : bufferData) {
            if (counter > index) {
                break;
            }
            stringBuffer.append(counter).append(". ").append(data.getPlayerName()).append("  ").append(data.getLifeCounterOld()).append(" -> ").append(data.getLifeCounterNew());
            stringBuffer.append("  ").append(data.getPoisonCounterOld()).append(" -> ").append(data.getPoisonCounterNew());
            stringBuffer.append("  ").append(data.getEnergyCounterOld()).append(" -> ").append(data.getEnergyCounterNew()).append("\n");
            counter++;
        }
        String s = stringBuffer.toString();
        return s;
    }

    public int getElement() {
        return this.element;
    }

}
