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

package com.liferay.extensions.maven.profile;

import com.liferay.blade.cli.TestUtil;

import java.io.File;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Christopher Bryan Boyd
 */
public class ServerCommandsMavenTest {

	@Test
	public void testServerInit() throws Exception {
		File workspaceDir = temporaryFolder.newFolder("build", "test", "workspace");

		String[] args = {"--base", workspaceDir.getPath(), "init", "-p", "maven"};

		TestUtil.runBlade(args);

		args = new String[] {"--base", workspaceDir.getPath(), "server", "init"};

		File bundlesDir = new File(workspaceDir.getPath(), "bundles");

		Assert.assertFalse(bundlesDir.exists());

		TestUtil.runBlade(args);

		Assert.assertTrue(bundlesDir.exists());
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

}