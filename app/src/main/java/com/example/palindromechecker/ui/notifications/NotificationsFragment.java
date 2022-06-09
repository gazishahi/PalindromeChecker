package com.example.palindromechecker.ui.notifications;

import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palindromechecker.MyRecyclerViewAdapter;
import com.example.palindromechecker.R;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements View.OnClickListener  {

    private Button clearAll;
    private Button serialize;
    private EditText enterFile;
    private final List<String> dataSet = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;
    private int count = 0;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        clearAll = view.findViewById(R.id.clearAll);
        clearAll.setOnClickListener(this);
        serialize = view.findViewById(R.id.serialize);
        serialize.setOnClickListener(this);
        enterFile = view.findViewById(R.id.enterFile);
        RecyclerView entryList = view.findViewById(R.id.entryList);
        entryList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getActivity(), dataSet);
        entryList.setAdapter(adapter);
        dataSet.clear();
        NotificationsViewModel viewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dataSet.add(s);
                adapter.notifyItemInserted(count);
                count++;
            }
        });
        return view;
    }

    @Override
    public void onClick (View v) {
        if (v == clearAll) {
            dataSet.clear();
            adapter.notifyItemRangeRemoved(0, count);
            count = 0;
        }
        //Serialize the list of entries
        if (v == serialize) {
            String fileName = enterFile.getText().toString() + ".xml";
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            if (!enterFile.getText().toString().replaceAll("[\\W]", "").isEmpty()) {
                try {
                    //Deprecated Method to Save to Documents folder
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    File file = new File(dir, fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    serializer.setOutput(writer);
                    serializer.startDocument("UTF-8", true);
                    serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    serializer.startTag("", "List");
                    serializer.attribute("", "String","dataSet");
                    for (int i = 0; i < dataSet.size(); i++) {
                        String entry = dataSet.get(i);
                        serializer.startTag("", "item");
                        serializer.text(entry);
                        serializer.endTag("", "item");
                    }
                    serializer.endTag("", "List");
                    serializer.endDocument();
                    serializer.flush();
                    String finalize = writer.toString();
                    fos.write(finalize.getBytes(StandardCharsets.UTF_8));
                    fos.close();
                    String success = "File saved in " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
                    Snackbar.make(requireContext(), view, success, 3000).show();
                }
                catch(IOException e) {
                e.printStackTrace();
                }
            }
            else {
                Snackbar.make(requireContext(), view, "Invalid File Name", 3000).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}