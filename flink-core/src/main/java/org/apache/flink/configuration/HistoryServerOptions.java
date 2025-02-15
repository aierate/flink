/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.configuration;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.configuration.description.Description;

import static org.apache.flink.configuration.ConfigOptions.key;
import static org.apache.flink.configuration.description.TextElement.code;

/** The set of configuration options relating to the HistoryServer. */
@PublicEvolving
public class HistoryServerOptions {

    /**
     * The interval at which the HistoryServer polls {@link
     * HistoryServerOptions#HISTORY_SERVER_ARCHIVE_DIRS} for new archives.
     */
    public static final ConfigOption<Long> HISTORY_SERVER_ARCHIVE_REFRESH_INTERVAL =
            key("historyserver.archive.fs.refresh-interval")
                    .defaultValue(10000L)
                    .withDescription(
                            "Interval in milliseconds for refreshing the archived job directories.");

    /** Comma-separated list of directories which the HistoryServer polls for new archives. */
    public static final ConfigOption<String> HISTORY_SERVER_ARCHIVE_DIRS =
            key("historyserver.archive.fs.dir")
                    .noDefaultValue()
                    .withDescription(
                            "Comma separated list of directories to fetch archived jobs from. The history server will"
                                    + " monitor these directories for archived jobs. You can configure the JobManager to archive jobs to a"
                                    + " directory via `jobmanager.archive.fs.dir`.");

    /** If this option is enabled then deleted job archives are also deleted from HistoryServer. */
    public static final ConfigOption<Boolean> HISTORY_SERVER_CLEANUP_EXPIRED_JOBS =
            key("historyserver.archive.clean-expired-jobs")
                    .defaultValue(false)
                    .withDescription(
                            String.format(
                                    "Whether HistoryServer should cleanup jobs"
                                            + " that are no longer present `%s`.",
                                    HISTORY_SERVER_ARCHIVE_DIRS.key()));

    /** The local directory used by the HistoryServer web-frontend. */
    public static final ConfigOption<String> HISTORY_SERVER_WEB_DIR =
            key("historyserver.web.tmpdir")
                    .noDefaultValue()
                    .withDescription(
                            "Local directory that is used by the history server REST API for temporary files.");

    /** The address under which the HistoryServer web-frontend is accessible. */
    public static final ConfigOption<String> HISTORY_SERVER_WEB_ADDRESS =
            key("historyserver.web.address")
                    .noDefaultValue()
                    .withDescription("Address of the HistoryServer's web interface.");

    /** The port under which the HistoryServer web-frontend is accessible. */
    public static final ConfigOption<Integer> HISTORY_SERVER_WEB_PORT =
            key("historyserver.web.port")
                    .defaultValue(8082)
                    .withDescription("Port of the HistoryServers's web interface.");

    /** The refresh interval for the HistoryServer web-frontend in milliseconds. */
    public static final ConfigOption<Long> HISTORY_SERVER_WEB_REFRESH_INTERVAL =
            key("historyserver.web.refresh-interval")
                    .defaultValue(10000L)
                    .withDescription(
                            "The refresh interval for the HistoryServer web-frontend in milliseconds.");

    /**
     * Enables/Disables SSL support for the HistoryServer web-frontend. Only relevant if {@link
     * SecurityOptions#SSL_REST_ENABLED} is enabled.
     */
    public static final ConfigOption<Boolean> HISTORY_SERVER_WEB_SSL_ENABLED =
            key("historyserver.web.ssl.enabled")
                    .defaultValue(false)
                    .withDescription(
                            "Enable HTTPs access to the HistoryServer web frontend. This is applicable only when the"
                                    + " global SSL flag security.ssl.enabled is set to true.");

    public static final ConfigOption<Integer> HISTORY_SERVER_RETAINED_JOBS =
            key("historyserver.archive.retained-jobs")
                    .intType()
                    .defaultValue(-1)
                    .withDescription(
                            Description.builder()
                                    .text(
                                            String.format(
                                                    "The maximum number of jobs to retain in each archive directory defined by `%s`. ",
                                                    HISTORY_SERVER_ARCHIVE_DIRS.key()))
                                    .text(
                                            "If set to `-1`(default), there is no limit to the number of archives. ")
                                    .text(
                                            "If set to `0` or less than `-1` HistoryServer will throw an %s. ",
                                            code("IllegalConfigurationException"))
                                    .build());

    private HistoryServerOptions() {}
}
