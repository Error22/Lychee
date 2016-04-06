package com.error22.lychee.server;

import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.network.NetworkServer;
import com.error22.lychee.util.Util;

public class LycheeServer {
	private static Logger log = LogManager.getLogger();
	public static LycheeServer INSTANCE;
	private HashMap<UUID, IProject> projects;

	public void init() {
		projects = new HashMap<UUID, IProject>();

		// JavaProject exampleProject = new JavaProject(UUID.randomUUID(),
		// "Example", new File("Projects/Example"));
		// addProject(exampleProject);
		// exampleProject.addJar(UUID.randomUUID(), "TestInput2.jar",
		// JarType.Main);
		// exampleProject.loadAllJars();
		//
		// // for (Entry<UUID, ManagedJar> entry : project.getJars().entrySet())
		// {
		// // log.info(" " + entry.getValue().getName());
		// // }
		// //
		// // for (Entry<UUID, ManagedClass> entry :
		// // project.getClasses().entrySet()) {
		// // log.info(" " + entry.getValue().getOutputPath());
		// // }
		// //
		// // ManagedClass result = Util.single(
		// // project.searchForClass(EnumSet.of(ClassSearchParam.SourcePath),
		// // "com/example/ExampleClass", false));
		// //
		// // log.info(" "+result.getSourceName());
		//
		// Snapshot ss1 = new Snapshot(exampleProject, UUID.randomUUID(), null,
		// new ActionRenameClass("com/example/ExampleClass",
		// "new/place/NewName"));
		// Snapshot ss2 = new Snapshot(exampleProject, UUID.randomUUID(), ss1,
		// new ActionRenameClass("com/Example/OtherExample",
		// "another/place/BadExample"));
		//
		// ss1.generateCache(true);
		// ss2.generateCache(true);
		//
		// for (ManagedClass clazz : exampleProject.getClasses()) {
		// log.info(" " + clazz.getName());
		// }
		//
		// for (IProject project : projects.values()) {
		//
		// System.out.println(" " + project.getName());
		//
		// }

		// snapshot.get

		NetworkServer server = new NetworkServer();
		server.start(null, 1234, TestNetworkHandler.class);

		while (true) {
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (Exception e) {
			}
		}

	}

	private void addProject(IProject project) {
		projects.put(project.getId(), project);
	}

	public HashMap<UUID, IProject> getProjects() {
		return projects;
	}

	public static void main(String[] args) {
		Util.tieSystemOutAndErrToLog();
		log.info("Starting!");
		INSTANCE = new LycheeServer();
		INSTANCE.init();

	}
}
