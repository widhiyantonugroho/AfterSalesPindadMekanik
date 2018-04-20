package com.pindad.aftersalespindadmekanik.Fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pindad.aftersalespindadmekanik.Adapter.MessagesAdapter;
import com.pindad.aftersalespindadmekanik.EmailListActivity;
import com.pindad.aftersalespindadmekanik.Helper.DividerItemDecoration;
import com.pindad.aftersalespindadmekanik.Model.Message;
import com.pindad.aftersalespindadmekanik.R;
import com.pindad.aftersalespindadmekanik.RestAPI.ApiClient;
import com.pindad.aftersalespindadmekanik.RestAPI.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutboxFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MessagesAdapter.MessageAdapterListener{
    public View empty;
    public TextView emptyTextView;

    private List<Message> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessagesAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmailListActivity.ActionModeCallback actionModeCallback;
    //    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    public OutboxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MessagesAdapter(getActivity(), messages, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        actionModeCallback = new EmailListActivity.ActionModeCallback();

        // show loader and fetch messages
//        swipeRefreshLayout.post(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        getInbox();
//                    }
//                }
//        );

        return rootView;
    }

    public void initilizeView(View rootView){
        empty = rootView.findViewById(R.id.empty_view);
        emptyTextView = (TextView) rootView.findViewById(R.id.empty_view);
    }

//    private void getInbox() {
//        swipeRefreshLayout.setRefreshing(true);
//
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//
//        Call<List<Message>> call = apiService.getInbox();
//        call.enqueue(new Callback<List<Message>>() {
//            @Override
//            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
//                // clear the inbox
//                messages.clear();
//
//                // add all the messages
//                // messages.addAll(response.body());
//
//                // TODO - avoid looping
//                // the loop was performed to add colors to each message
//                for (Message message : response.body()) {
//                    // generate a random color
//                    message.setColor(getRandomMaterialColor("400"));
//                    messages.add(message);
//                }
//
//                mAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(Call<List<Message>> call, Throwable t) {
//                Toast.makeText(getContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

    /**
     * chooses a random color from array.xml
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", String.valueOf(getActivity()));

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }


    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
//        getInbox();
    }


    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
//            actionMode = startSupportActionMode(actionModeCallback);
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);

//        if (actionMode != null) {
//            toggleSelection(position);
//        }

    }

    @Override
    public void onIconImportantClicked(int position) {
        // Star icon is clicked,
        // mark the message as important
        Message message = messages.get(position);
        message.setImportant(!message.isImportant());
        messages.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {
            // read the message which removes bold from the row
            Message message = messages.get(position);
            message.setRead(true);
            messages.set(position, message);
            mAdapter.notifyDataSetChanged();

            Toast.makeText(getContext(), "Read: " + message.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        // long press is performed, enable action mode
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
//            actionMode = getActivity().startSupportActionMode(actionModeCallback);
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);

        }
        toggleSelection(position);

//        if (actionMode != null) {
//            toggleSelection(position);
//        }
    }

//    @Override
//    public boolean onItemLongClicked(int position) {
//        if (actionMode != null) {
//            ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
//        }
//
//        toggleSelection(position);
//
//        return true;
//    }

//    private ActionMode startSupportActionMode(ActionModeCallback actionModeCallback) {
//        return null;
//    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // delete all the selected messages
                    deleteMessages();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                    // mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    // deleting the messages from recycler view
    private void deleteMessages() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

}
