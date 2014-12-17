package se.mah.da401a_databases_1;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private EditText name, code;

  private DBController dbController;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment PersonFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static PersonFragment newInstance() {
    PersonFragment fragment = new PersonFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }



  public PersonFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    dbController = new DBController(getActivity());
  }

  @Override
  public void onResume() {
    super.onResume();

    dbController.open();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root =  inflater.inflate(R.layout.fragment_person, container, false);

    name = (EditText) root.findViewById(R.id.name);
    code = (EditText) root.findViewById(R.id.code);

    Button button = (Button) root.findViewById(R.id.create);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String newName = name.getText().toString();
        String newCode = code.getText().toString();

        long id = dbController.addPerson(newName, newCode);
        Toast.makeText(getActivity(), "Person with id " + id + " was created", Toast.LENGTH_SHORT).show();
      }
    });

    return root;
  }

  @Override
  public void onPause() {
    super.onPause();

    dbController.close();
  }
}
