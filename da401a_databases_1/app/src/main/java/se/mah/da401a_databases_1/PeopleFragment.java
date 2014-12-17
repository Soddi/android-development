package se.mah.da401a_databases_1;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PeopleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeopleFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private ListView people;
  private PeopleAdapter adapter;

  private DBController dbController;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment PeopleFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static PeopleFragment newInstance(String param1, String param2) {
    PeopleFragment fragment = new PeopleFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public PeopleFragment() {
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

    Cursor c = dbController.getPeople();
    adapter = new PeopleAdapter(getActivity(), c, true);
    people.setAdapter(adapter);
  }

  @Override
  public void onPause() {
    super.onPause();
    dbController.close();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root =  inflater.inflate(R.layout.fragment_people, container, false);

    people = (ListView) root.findViewById(R.id.peoplelist);
    people.setAdapter(adapter);

    Button button = (Button) root.findViewById(R.id.add);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.container, PersonFragment.newInstance(), "person").addToBackStack("person").commit();
      }
    });

    return root;
  }


}
