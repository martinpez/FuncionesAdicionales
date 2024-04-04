package moviles.funcionesadicionales;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    ListView lista;
    ArrayList listaItems = new ArrayList();
    ProgressBar barradeCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        lista = (ListView) findViewById( R.id.listaSync );
        barradeCarga = (ProgressBar) findViewById( R.id.progressBar );
        llenarLista();
        llenarLista2();
    }

    public void llenarLista2(){
        //new ClaseAsincrona.execute();
    }

    public void llenarLista(){
        int tiempo = 600;
        Future<ArrayList> future = executorService.submit( new Callable<ArrayList>() {
            @Override
            public ArrayList call() throws Exception {
                ArrayList listaDeCarga = new ArrayList();
                for(int i=0; i<10;i++){
                    listaDeCarga.add( "Objeto"+i );
                    barradeCarga.incrementProgressBy( 10 );
                    sleep( tiempo);
                }
                return listaDeCarga;
            }
        });


        new Handler( Looper.getMainLooper()).post( new Runnable() {
            @Override
            public void run() {
                try {
                    // Obtener el resultado del Future y actualizar la UI
                    listaItems = future.get();
                    //UI
                    ArrayAdapter adapatadorLista = new ArrayAdapter<>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_checked,
                            listaItems);
                    lista.setAdapter(adapatadorLista);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}