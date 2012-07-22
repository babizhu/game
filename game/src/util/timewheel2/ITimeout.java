package util.timewheel2;

/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */


/**
 * A handle associated with a {@link ITimerTask} that is returned by a
 * {@link ITimer}.
 *
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 */
public interface ITimeout {

    /**
     * Returns the {@link ITimer} that created this handle.
     */
    ITimer getTimer();

    /**
     * Returns the {@link ITimerTask} which is associated with this handle.
     */
    ITimerTask getTask();

    /**
     * Returns {@code true} if and only if the {@link ITimerTask} associated
     * with this handle has been expired.
     */
    boolean isExpired();

    /**
     * Returns {@code true} if and only if the {@link ITimerTask} associated
     * with this handle has been cancelled.
     */
    boolean isCancelled();

    /**
     * Cancels the {@link ITimerTask} associated with this handle.  It the
     * task has been executed or cancelled already, it will return with no
     * side effect.
     */
    void cancel();
}
