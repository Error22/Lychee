package com.error22.lychee.server;

import java.io.File;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.server.managed.ClassSearchParam;
import com.error22.lychee.server.managed.JarType;
import com.error22.lychee.server.managed.ManagedClass;
import com.error22.lychee.server.managed.ManagedJar;
import com.error22.lychee.server.managed.Project;
import com.error22.lychee.server.snapshots.ActionRenameClass;
import com.error22.lychee.server.snapshots.Snapshot;

public class Server {
	private static Logger log = LogManager.getLogger();

	public static void main(String[] args) {
		Util.tieSystemOutAndErrToLog();
		log.info("Starting!");

		Project project = new Project("Example", new File("Projects/Example"));
		project.addJar(UUID.randomUUID(), "TestInput2.jar", JarType.Main);

		project.loadAllJars();

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

		Snapshot ss1 = new Snapshot(project, UUID.randomUUID(), null,
				new ActionRenameClass("com/example/ExampleClass", "new/place/NewName"));
		Snapshot ss2 = new Snapshot(project, UUID.randomUUID(), ss1,
				new ActionRenameClass("com/Example/OtherExample", "another/place/BadExample"));
		
		ss1.generateCache(true);
		ss2.generateCache(true);
		
		
		

		// snapshot.get

	}
}
