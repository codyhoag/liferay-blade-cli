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

package com.liferay.blade.upgrade.liferay70.apichanges;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.liferay.blade.api.SearchResult;
import com.liferay.blade.eclipse.provider.JavaFileJDT;
import com.liferay.blade.eclipse.provider.PlatformUtil;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class DDMLegacyAPITest {
	final File testFile = new File(
			"projects/legacy-apis-ant-portlet/docroot/WEB-INF/src/com/liferay/JournalArticleAssetRendererFactory.java");
	DDMLegacyAPI component;

	@Before
	public void beforeTest() {
		assertTrue(testFile.exists());
		component = new DDMLegacyAPI();
	}

	@Test
	public void dDMLegacyAPITest() throws Exception {
		List<SearchResult> results = component.searchFile(
			testFile, new JavaFileJDT(testFile));

		assertNotNull(results);
		assertEquals(5, results.size());

		SearchResult problem = results.get(0);

		assertEquals(36, problem.startLine);

		if (PlatformUtil.isWindows()) {
			assertEquals(1704, problem.startOffset);
			assertEquals(1779, problem.endOffset);
		}
		else {
			assertEquals(1669, problem.startOffset);
			assertEquals(1744, problem.endOffset);
		}

		problem = results.get(1);

		assertEquals(134, problem.startLine);

		if (PlatformUtil.isWindows()) {
			assertTrue(problem.startOffset >= 4829 && problem.startOffset <= 4832);
			assertTrue(problem.endOffset >= 4886 && problem.endOffset <= 4889);
		}
		else {
			assertTrue(problem.startOffset >= 4696 && problem.startOffset <= 4699);
			assertTrue(problem.endOffset >= 4753 && problem.endOffset <= 4756);
		}

		problem = results.get(2);

		assertEquals(147, problem.startLine);

		if (PlatformUtil.isWindows()) {
			assertTrue(problem.startOffset >= 5177 && problem.startOffset <= 5180);
			assertTrue(problem.endOffset >= 5234 && problem.endOffset <= 5237);
		}
		else {
			assertTrue(problem.startOffset >= 5031 && problem.startOffset <= 5034);
			assertTrue(problem.endOffset >= 5088 && problem.endOffset <= 5091);

		}
		problem = results.get(3);

		assertEquals(37, problem.startLine);

		if (PlatformUtil.isWindows()) {
			assertTrue(problem.startOffset >= 1789 && problem.startOffset <= 1792);
			assertTrue(problem.endOffset >= 1859 && problem.endOffset <= 1862);
		}
		else {
			assertEquals(1753, problem.startOffset);
			assertEquals(1823, problem.endOffset);
		}

		problem = results.get(4);

		assertEquals(162, problem.startLine);

		if (PlatformUtil.isWindows()) {
			assertTrue(problem.startOffset >= 5573 && problem.startOffset <= 5576);
			assertTrue(problem.endOffset >= 5690 && problem.endOffset <= 5693);
		}
		else {
			assertTrue(
				String.valueOf(problem.startOffset),
				problem.startOffset >= 5412 && problem.startOffset <= 5415);
			assertTrue(
				String.valueOf(problem.endOffset),
				problem.endOffset >= 5527 && problem.endOffset <= 5530);
		}
	}

}
