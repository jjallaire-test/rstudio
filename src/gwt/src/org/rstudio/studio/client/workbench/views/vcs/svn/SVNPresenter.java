/*
 * SVNPresenter.java
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.workbench.views.vcs.svn;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.rstudio.core.client.Size;
import org.rstudio.core.client.command.CommandBinder;
import org.rstudio.core.client.command.Handler;
import org.rstudio.core.client.files.FileSystemItem;
import org.rstudio.studio.client.common.SimpleRequestCallback;
import org.rstudio.studio.client.common.console.ConsoleProcess;
import org.rstudio.studio.client.common.satellite.SatelliteManager;
import org.rstudio.studio.client.common.vcs.SVNServerOperations;
import org.rstudio.studio.client.common.vcs.StatusAndPath;
import org.rstudio.studio.client.vcs.VCSApplicationParams;
import org.rstudio.studio.client.workbench.WorkbenchView;
import org.rstudio.studio.client.workbench.commands.Commands;
import org.rstudio.studio.client.workbench.views.vcs.BaseVcsPresenter;
import org.rstudio.studio.client.workbench.views.vcs.common.ConsoleProgressDialog;
import org.rstudio.studio.client.workbench.views.vcs.common.ProcessCallback;
import org.rstudio.studio.client.workbench.views.vcs.svn.model.SVNState;

import java.util.ArrayList;

public class SVNPresenter extends BaseVcsPresenter
{
   interface Binder extends CommandBinder<Commands, SVNPresenter>
   {
   }

   public interface Display extends WorkbenchView, IsWidget
   {
      HasClickHandlers getDiffButton();
      HasClickHandlers getAddFilesButton();
      HasClickHandlers getDeleteFilesButton();
      HasClickHandlers getRevertFilesButton();
      HasClickHandlers getUpdateButton();
      HasClickHandlers getCommitButton();
      ArrayList<StatusAndPath> getSelectedItems();
   }

   @Inject
   public SVNPresenter(Display view,
                       Commands commands,
                       SVNServerOperations server,
                       SVNState svnState,
                       SatelliteManager satelliteManager)
   {
      super(view);
      view_ = view;
      server_ = server;
      svnState_ = svnState;
      satelliteManager_ = satelliteManager;

      GWT.<Binder>create(Binder.class).bind(commands, this);

      view_.getDiffButton().addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            showReviewPane(false);
         }
      });

      view_.getAddFilesButton().addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            ArrayList<String> paths = getPathArray();

            if (paths.size() > 0)
               server_.svnAdd(paths, new ProcessCallback("SVN Add"));
         }
      });

      view_.getDeleteFilesButton().addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            ArrayList<String> paths = getPathArray();

            if (paths.size() > 0)
               server_.svnDelete(paths, new ProcessCallback("SVN Delete"));
         }
      });

      view_.getRevertFilesButton().addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            ArrayList<String> paths = getPathArray();

            if (paths.size() > 0)
               server_.svnRevert(paths, new ProcessCallback("SVN Revert"));
         }
      });

      view_.getUpdateButton().addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            server_.svnUpdate(new SimpleRequestCallback<ConsoleProcess>()
            {
               @Override
               public void onResponseReceived(ConsoleProcess response)
               {
                  new ConsoleProgressDialog("SVN Update", response).showModal();
               }
            });
         }
      });

      view_.getCommitButton().addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            // TODO: implement
         }
      });
   }

   private void showReviewPane(boolean showHistory)
   {
      // setup params
      VCSApplicationParams params = VCSApplicationParams.create(
                                          showHistory,
                                          null,
                                          view_.getSelectedItems());

      // open the window
      satelliteManager_.openSatellite("review_changes",
                                      params,
                                      getPreferredReviewPanelSize());
   }

   private Size getPreferredReviewPanelSize()
   {
      Size windowBounds = new Size(Window.getClientWidth(),
                                   Window.getClientHeight());

      return new Size(Math.min(windowBounds.width - 100, 1000),
                      windowBounds.height - 25);
   }

   private ArrayList<String> getPathArray()
   {
      ArrayList<StatusAndPath> items = view_.getSelectedItems();
      ArrayList<String> paths = new ArrayList<String>();
      for (StatusAndPath item : items)
         paths.add(item.getPath());
      return paths;
   }

   @Override
   public Widget asWidget()
   {
      return view_.asWidget();
   }
   
   @Override
   public void onVcsCommit()
   {
      
      
   }

   @Override
   public void onVcsShowHistory()
   {
      
      
   }

   @Override
   public void onVcsPull()
   {
      // git specific,  not supported by svn
   }

   @Override
   public void onVcsPush()
   {
      // git specific,  not supported by svn
   }
   
    @Override
   public void onVcsCreateBranch()
   {
       // git specific,  not supported by svn
   }
   
   @Override
   public void showHistory(FileSystemItem fileFilter)
   {
      
      
   }
   
   @Override
   public void showDiff(FileSystemItem file)
   {
     
      
   }
   
   @Override
   public void revertFile(FileSystemItem file)
   {
      
   }
   

   @Handler
   void onVcsRefresh()
   {
      svnState_.refresh(true);
   }

   private final Display view_;
   private final SVNServerOperations server_;
   private final SVNState svnState_;
   private final SatelliteManager satelliteManager_;
   
   
}
