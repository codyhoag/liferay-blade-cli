/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.cli;

import com.liferay.blade.cli.command.BaseArgs;
import com.liferay.blade.cli.command.BaseCommand;
import com.liferay.blade.cli.util.BladeUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Christopher Bryan Boyd
 * @author Gregory Amerson
 */
public class ExtensionsTest {

	@Before
	public void cleanTestUserHome() throws Exception {
		TestUtil.deleteDir(_USER_HOME.toPath());
	}

	@Test
	public void testArgsSort() throws Exception {
		String[] args = {"--base", "/foo/bar/dir/", "--flag1", "extension", "install", "/path/to/jar.jar", "--flag2"};

		Map<String, BaseCommand<? extends BaseArgs>> commands = new Extensions().getCommands();

		String[] sortedArgs = Extensions.sortArgs(commands, args);

		boolean correctSort = false;

		for (String arg : sortedArgs) {
			if (Objects.equals(arg, "extension install")) {
				correctSort = true;
			}
		}

		Assert.assertTrue(correctSort);
	}

	@Test
	public void testLoadCommandsBuiltIn() throws Exception {
		Map<String, BaseCommand<? extends BaseArgs>> commands = new Extensions().getCommands();

		Assert.assertNotNull(commands);

		Assert.assertEquals(commands.toString(), _NUM_BUILTIN_COMMANDS, commands.size());
	}

	@Test
	public void testLoadCommandsWithCustomExtension() throws Exception {
		_setupTestExtensions();

		Map<String, BaseCommand<? extends BaseArgs>> commands = new Extensions().getCommands();

		Assert.assertNotNull(commands);

		Assert.assertEquals(commands.toString(), _NUM_BUILTIN_COMMANDS + 1, commands.size());
	}

	@Test
	public void testProjectTemplatesBuiltIn() throws Exception {
		Map<String, String> templates = BladeUtil.getTemplates();

		Assert.assertNotNull(templates);

		Assert.assertEquals(templates.toString(), _NUM_BUILTIN_TEMPLATES, templates.size());
	}

	@Test
	public void testProjectTemplatesWithCustom() throws Exception {
		_setupTestExtensions();

		Map<String, String> templates = BladeUtil.getTemplates();

		Assert.assertNotNull(templates);

		Assert.assertEquals(templates.toString(), _NUM_BUILTIN_TEMPLATES + 1, templates.size());
	}

	private static void _setupTestExtensions() throws Exception {
		File extensionsDir = new File(_USER_HOME, ".blade/extensions");

		extensionsDir.mkdirs();

		Assert.assertTrue("Unable to create test extensions dir.", extensionsDir.exists());

		Path extensionsPath = extensionsDir.toPath();

		Consumer<String> copySampleJarFile = prop -> {
			File sampleJarFile = new File(System.getProperty(prop));

			Assert.assertTrue(sampleJarFile.getAbsolutePath() + " does not exist.", sampleJarFile.exists());

			Path sampleJarPath = extensionsPath.resolve(sampleJarFile.getName());

			try {
				Files.copy(sampleJarFile.toPath(), sampleJarPath, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (IOException ioe) {
			}

			Assert.assertTrue(Files.exists(sampleJarPath));
		};

		copySampleJarFile.accept("sampleCommandJarFile");
		copySampleJarFile.accept("sampleTemplateJarFile");
	}

	private static final int _NUM_BUILTIN_COMMANDS = 17;

	private static final int _NUM_BUILTIN_TEMPLATES = 36;

	private static final File _USER_HOME = new File(System.getProperty("user.home"));

}