package com.error22.lychee.server;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.server.java.JavaProject;
import com.error22.lychee.server.managed.JarType;
import com.error22.lychee.server.managed.ManagedClass;
import com.error22.lychee.server.network.NetworkManager;
import com.error22.lychee.server.snapshots.ActionRenameClass;
import com.error22.lychee.server.snapshots.Snapshot;

public class LycheeServer {
	private static Logger log = LogManager.getLogger();
	public static LycheeServer INSTANCE;
	private static HashMap<UUID, IProject> projects;
	private static NetworkManager networkManager;

	public void init() {
		projects = new HashMap<UUID, IProject>();

		networkManager = new NetworkManager(this, null, 1234);
		networkManager.start();
		
		boolean abc = true;
		while(abc){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

		JavaProject exampleProject = new JavaProject(UUID.randomUUID(), "Example", new File("Projects/Example"));
		addProject(exampleProject);
		exampleProject.addJar(UUID.randomUUID(), "TestInput2.jar", JarType.Main);
		exampleProject.loadAllJars();

		// for (Entry<UUID, ManagedJar> entry : project.getJars().entrySet()) {
		// log.info(" " + entry.getValue().getName());
		// }
		//
		// for (Entry<UUID, ManagedClass> entry :
		// project.getClasses().entrySet()) {
		// log.info(" " + entry.getValue().getOutputPath());
		// }
		//
		// ManagedClass result = Util.single(
		// project.searchForClass(EnumSet.of(ClassSearchParam.SourcePath),
		// "com/example/ExampleClass", false));
		//
		// log.info(" "+result.getSourceName());

		Snapshot ss1 = new Snapshot(exampleProject, UUID.randomUUID(), null,
				new ActionRenameClass("com/example/ExampleClass", "new/place/NewName"));
		Snapshot ss2 = new Snapshot(exampleProject, UUID.randomUUID(), ss1,
				new ActionRenameClass("com/Example/OtherExample", "another/place/BadExample"));

		ss1.generateCache(true);
		ss2.generateCache(true);

		for (ManagedClass clazz : exampleProject.getClasses()) {
			log.info(" " + clazz.getName());
		}

		for (IProject project : projects.values()) {

			System.out.println(" " + project.getName());

		}

		// snapshot.get

	}

	private void addProject(IProject project) {
		projects.put(project.getId(), project);
	}

	public static void main(String[] args) {
		Util.tieSystemOutAndErrToLog();
		log.info("Starting!");
		INSTANCE = new LycheeServer();
		INSTANCE.init();

	}
}
