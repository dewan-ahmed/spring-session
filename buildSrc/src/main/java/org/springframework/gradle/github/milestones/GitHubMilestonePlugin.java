/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.gradle.github.milestones;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitHubMilestonePlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		project.getTasks().register("gitHubCheckMilestoneHasNoOpenIssues", GitHubMilestoneHasNoOpenIssuesTask.class, new Action<GitHubMilestoneHasNoOpenIssuesTask>() {
			@Override
			public void execute(GitHubMilestoneHasNoOpenIssuesTask githubCheckMilestoneHasNoOpenIssues) {
				githubCheckMilestoneHasNoOpenIssues.setGroup("Release");
				githubCheckMilestoneHasNoOpenIssues.setDescription("Checks if there are any open issues for the specified repository and milestone");
				githubCheckMilestoneHasNoOpenIssues.setMilestoneTitle((String) project.findProperty("nextVersion"));
				if (project.hasProperty("githubAccessToken")) {
					githubCheckMilestoneHasNoOpenIssues.setGitHubAccessToken((String) project.findProperty("gitHubAccessToken"));
				}
			}
		});
	}
}
