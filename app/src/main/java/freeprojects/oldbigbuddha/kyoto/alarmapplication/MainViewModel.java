package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by developer on 17/05/31.
 */

public class MainViewModel {

    public void onClickToNewCreate( View view ) {
        Context context = view.getContext();
        Intent intent = new Intent( context, NewCreateActivity.class );
        context.startActivity( intent );
    }

}
