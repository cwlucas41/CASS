package edu.slu.cass;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


import edu.slu.wsd.Algorithm;
import edu.slu.wsd.WSD;
import edu.slu.wsd.languageTool.Language;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

/**
 * Wrapper class for LibreOffice Basic to interact with. Translates calls into WSD calls and translates returns from List to array types.
 * @author cwlucas41
 *
 */
public class Cass {
	
	WSD wsd;
	JTree tree;
	DefaultTreeModel dm;
	JDialog dialog;
	private String target;
	private JOptionPane optPane;
	private NodeSelectionSaver result;
	private static final List<Character> removePunct = Arrays.asList('.', ',', '!', '?', '-', ')', '(', '[', ']', '{', '}', ';', ':', '/', '\\');
	
	/**
	 * Constructor for LibreOfficeCass. Converts language String into Language Enumeration type.
	 * @param leftContext - String of words left of the target word in corpus
	 * @param target - target word String
	 * @param rightContext - String of words right of the target word in corpus
	 * @param language - String representing language of test
	 */
	public Cass(String leftContext, String target, String rightContext, String language) {
		
		
		if ((target != null) && (target.length() > 1) && (removePunct.contains(target.charAt(target.length() - 1)))) {
			target = target.substring(0, target.length() - 1);
	    }
		
		this.target = target;
		result = new NodeSelectionSaver();
		
		switch (language) {
		case "English":
			wsd = new WSD(leftContext, target, rightContext, Language.EN);
			break;

		default:
			break;
		}
	}
	
	/**
	 * Calls WSD algorithm using a string for the algorithm field (translates into Algorithm Enum). 
	 * Then gets synonyms for each sense and translates result from List type to array type
	 * @param algorithm String
	 * @return String array of senses containing synonyms
	 */
	public List<CASSWordSense> getSynonyms(String algorithm) {
		List<CASSWordSense> rankedSenses = null;
		
		switch (algorithm) {
		case "Lesk":
			rankedSenses = wsd.rankSensesUsing(Algorithm.LESK);
			break;

		default:
			break;
		}
		
		return rankedSenses;
	}
	
	public String getSynonym(String algorithm) {
		List<CASSWordSense> rankedSenses = getSynonyms(algorithm);
		return showGUI(rankedSenses);
	}
	
	List<Set<String>> convertToSynonyms(List<CASSWordSense> senses) {
		List<Set<String>> result = new ArrayList<Set<String>>();
		
		for (CASSWordSense sense : senses) {
			Set<String> synonyms = wsd.getlTool().getSynonyms(sense);
			synonyms.remove(wsd.getTarget());
			if (!synonyms.isEmpty()) {
				result.add(synonyms);
			}
		}
		
		return result;
	}
	
	private String showGUI(List<CASSWordSense> senses) {
		DefaultMutableTreeNode treeRoot = makeTree(senses);	    
	    
		JFrame frame = new JFrame("title");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel showPane = new JPanel();
	    showPane.setLayout(new BorderLayout()); 

	    dm = new DefaultTreeModel(treeRoot);
	    tree = new JTree(dm);
	    tree.setRootVisible(false);
	    tree.setShowsRootHandles(true);
	    tree.addTreeSelectionListener(result);
	    
	    for (int i = 0; i < tree.getRowCount(); i++) {
	    	tree.expandRow(i);
	    }
	    
	    JScrollPane treeView = new JScrollPane(tree);

	    showPane.add(treeView, BorderLayout.CENTER);

	    JComponent[] inputComponents = new JComponent[] {showPane};

	    Object[] opButtons = generateOptions(false);

	    optPane = new JOptionPane(inputComponents       
	            , JOptionPane.PLAIN_MESSAGE             
	            , JOptionPane.OK_CANCEL_OPTION             
	            , null                                      
	            , opButtons                             
	            , opButtons[0]);
	    optPane.setPreferredSize(new Dimension(400 ,500));

	    dialog = optPane.createDialog(null, "CASS");
	    dialog.setLocationRelativeTo(frame);
	    dialog.setVisible(true);
	    	    
	    if (optPane.getValue().toString() == "OK") {
	    	return result.getSelection();
	    } else {
	    	return target;
	    }
	}

	/**
	 * Converts list of senses to nested array of synonyms
	 * @param senses
	 * @return String array for senses containing array for synonyms
	 */
	
	protected String removeUnderscore(String original){
		String result = new String(original);
		if(original.contains("_")){
			result = original.replace("_", " ");
		}
		return result;
	}
	
	private String StringSlice(String s, int start, int end){
		String result = new String("");
		String tempSinceSpace = new String();
		for(int i=start; i < end; i++){
			if(s.charAt(i) == ' '){				
				result = result.concat(tempSinceSpace);
				tempSinceSpace = "";
			}
			tempSinceSpace+=s.charAt(i);
		}
		if(result.isEmpty()){
			result = tempSinceSpace;
		}
		return result;
		
	}
	
	
	
	protected String shortenSynopsis(String definition, Set<String> options){
		final int chars = 25;
		int counter = 0;
		int charcount = 3;
		String optionsStr = new String("(");
		for(String option : options){
			option = removeUnderscore(option);
			if(counter < charcount){
				optionsStr = optionsStr.concat(option);
				if(counter < options.size()-1 && counter < charcount-1){
					optionsStr = optionsStr.concat(", ");
				}
			}
			counter+=1;
		}
		//optionsStr = StringSlice(optionsStr,0, optionsStr.length()-2);
		optionsStr = optionsStr.concat(")");
		if(definition.length() > 50 ){			
			String shorten = new String("");
			shorten = shorten.concat(StringSlice(definition,0,chars));
			//shorten = shorten.concat("... ");
			//shorten = shorten.concat(optionsStr);
			optionsStr = optionsStr.concat(shorten);
			return optionsStr;
		}
		//definition = definition.concat(optionsStr);
		optionsStr = optionsStr.concat(definition);
		return optionsStr;
	}
	
	

private DefaultMutableTreeNode makeTree(List<CASSWordSense> senses) {
	
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Synsets");
	 
	    for (CASSWordSense sense : senses) {
	    	Set<String> synonyms = wsd.getlTool().getSynonyms(sense);
	    	synonyms.remove(wsd.getTarget());
	    	
	    	if (synonyms.size() > 0) {
		    	DefaultMutableTreeNode synsetNode = new DefaultMutableTreeNode(shortenSynopsis(removeUnderscore(wsd.getlTool().getDefinition(sense).split(";")[0]),synonyms));
		    	rootNode.add(synsetNode);
		    	
		    	for (String synonym : synonyms) {
		    		DefaultMutableTreeNode synonymNode = new DefaultMutableTreeNode(removeUnderscore(synonym));
		    		synsetNode.add(synonymNode);
		    	}
	    	}
	    }
		
		return rootNode;
	}
	
	private String[] generateOptions(boolean isValid) {
		if (isValid) {
			return new String[] {"OK", "Cancel"};
		} else {
			return new String[] {"Cancel"};
		}
	}
	
    class NodeSelectionSaver implements TreeSelectionListener {
    	private String selection;
    	
		@Override
		public void valueChanged(TreeSelectionEvent e) {
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
		    if (node == null) {
		    	return;
		    }

		    if (node.isLeaf()) {
		    	selection = node.toString();
		    	optPane.setOptions(generateOptions(true));
		    } else {
		    	optPane.setOptions(generateOptions(false));
		    }
		    optPane.repaint();
		}
		
		public String getSelection() {
			return selection;
		}
    }
}


