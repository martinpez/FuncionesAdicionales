package moviles.funcionesadicionales;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    String nombres[] = {"Juan" ,"Ana" , "Luisa", "Felipe","Pablo" , "David" , "Martin" , "Daniel","Dana","Laura" } ;
    ListView lista;
    ArrayList listaItems = new ArrayList(); // se creo un arraylis para guardar los objetos

    ProgressBar barraprogreso;
    int tiempo_barras = 600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.listaSync);
        barraprogreso = (ProgressBar) findViewById(R.id.progressBar);
        llamado(); // llamado de la funcion para la clase Asincrona

    }

    private void llamado(){
        new ClaseAsincrona().execute();
    }



    private class ClaseAsincrona extends AsyncTask<Void, Void, ArrayList<String>>{  // AsyncTask

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {  // doInBackgroud
            ArrayList<String> listacompleta = new ArrayList<>();
            for (int i = 0; i < nombres.length; i++) {
                listacompleta.add(nombres[i]);
                publishProgress(); // Actualiza la interfas de UI

                try {
                    // Cuando el hilo este dormido no sea interrumpido y pueda
                    // ver la interrupcion
                    Thread.sleep(tiempo_barras);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

            return listacompleta;


        }

        @Override
        protected void onProgressUpdate(Void... values) { // onProgressUpdate
            super.onProgressUpdate(values);
            barraprogreso.incrementProgressBy(  10);

        }

        @Override
        protected void onPostExecute(ArrayList<String> objeto) { // onPostExecute
            super.onPostExecute(objeto);
            listaItems = objeto;
            ArrayAdapter<String> listaAdaptada = new ArrayAdapter<>(  // aray que va adaptar los ojetos de la lista Items al arraylist
                    getApplicationContext(),
                    android.R.layout.simple_expandable_list_item_1,objeto);
            lista.setAdapter(listaAdaptada);

        }

    }

    @Override
    protected void onDestroy() {  // onDestroy
        super.onDestroy();
    }
}