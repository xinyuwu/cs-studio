package org.csstudio.opibuilder.script;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.csstudio.opibuilder.editparts.AbstractBaseEditPart;
import org.csstudio.opibuilder.preferences.PreferencesHelper;
import org.csstudio.rap.core.DisplayManager;
import org.csstudio.ui.util.thread.UIBundlingThread;
import org.csstudio.utility.pv.PV;
import org.eclipse.swt.widgets.Display;
import org.mozilla.javascript.Context;
import org.python.util.PythonInterpreter;

/**The factory to return the corresponding script store according to the script type.
 * @author Xihui Chen
 *
 */
public class ScriptStoreFactory {
	
	private static boolean pythonInterpreterInitialized = false;
	
	
	private static Map<Display, Context> displayContextMap = 
			new HashMap<Display, Context>();
	
	public static void initPythonInterpreter() throws Exception{
		if(pythonInterpreterInitialized)
			return;
		String pythonPath = PreferencesHelper.getPythonPath();
		if(pythonPath != null){
    		Properties props = new Properties();
    		props.setProperty("python.path", pythonPath); //$NON-NLS-1$
        	PythonInterpreter.initialize(System.getProperties(), props,
                    new String[] {""}); //$NON-NLS-1$
    	}
		pythonInterpreterInitialized = true;
	}	
	
	/**
	 * Must be called in UI Thread.
	 * @throws Exception 
	 */
	private static void initJSEngine() throws Exception {
		Context scriptContext = Context.enter();
		final Display display = Display.getCurrent();
		displayContextMap.put(display, scriptContext);
		DisplayManager.getInstance().addDisplayDisposeListener(display, new Runnable() {
			
			public void run() {
				displayContextMap.remove(display);
			}
		});
	}

	/**This method must be called in UI Thread!
	 * @param scriptData
	 * @param editpart
	 * @param pvArray
	 * @return
	 * @throws Exception
	 */
	public static AbstractScriptStore getScriptStore(
			ScriptData scriptData, AbstractBaseEditPart editpart, PV[] pvArray) throws Exception{
		boolean jsEngineInitialized = displayContextMap.containsKey(Display.getCurrent());
		if(scriptData.getPath() == null || scriptData.getPath().getFileExtension() == null){
			if(scriptData instanceof RuleScriptData){
				if(!jsEngineInitialized)
					initJSEngine();
				return new RhinoScriptStore(scriptData, editpart, pvArray);
			}
			else
				throw new RuntimeException("No Script Engine for this type of script");
		}
		String fileExt = scriptData.getPath().getFileExtension().trim().toLowerCase();
		if(fileExt.equals(ScriptService.JS)){ //$NON-NLS-1$
			if(!jsEngineInitialized)
				initJSEngine();
			return new RhinoScriptStore(scriptData, editpart, pvArray);
		}
		else if (fileExt.equals(ScriptService.PY)){ //$NON-NLS-1$
			if(!pythonInterpreterInitialized)
				initPythonInterpreter();
			return new JythonScriptStore(scriptData, editpart, pvArray);
		}
		else
			throw new RuntimeException("No Script Engine for this type of script");
	}
	
	/**This method must be executed in UI Thread!
	 * @return the script context.
	 * @throws Exception 
	 */
	public static Context getJavaScriptContext() throws Exception {
		Display display = Display.getCurrent();
		boolean jsEngineInitialized = displayContextMap.containsKey(display);
		if(!jsEngineInitialized)
			initJSEngine();		
		return displayContextMap.get(display);
	}
	
	public static void exit(){
		boolean jsEngineInitialized = displayContextMap.containsKey(Display.getCurrent());
		if(jsEngineInitialized)
			UIBundlingThread.getInstance().addRunnable(Display.getCurrent(), new Runnable(){
				public void run() {
					Context.exit();
				}
			});
	}
	
}