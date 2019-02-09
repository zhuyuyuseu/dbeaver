/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
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
package org.jkiss.dbeaver.ui.dashboard.view;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPart;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.controls.ListContentProvider;
import org.jkiss.dbeaver.ui.dashboard.control.DashboardItem;
import org.jkiss.dbeaver.ui.dashboard.control.DashboardList;
import org.jkiss.dbeaver.ui.dashboard.control.DashboardListViewer;
import org.jkiss.dbeaver.ui.dashboard.internal.UIDashboardActivator;
import org.jkiss.dbeaver.ui.dashboard.model.DashboardViewConfiguration;
import org.jkiss.dbeaver.ui.dashboard.model.DashboardViewContainer;
import org.jkiss.dbeaver.ui.dashboard.registry.DashboardDescriptor;
import org.jkiss.dbeaver.ui.dashboard.registry.DashboardRegistry;
import org.jkiss.dbeaver.ui.dialogs.BaseDialog;
import org.jkiss.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Dashboard view dialog
 */
public class DashboardViewDialog extends BaseDialog {

    private static final String DIALOG_ID = "DBeaver.DashboardViewDialog";//$NON-NLS-1$

    private final DashboardViewContainer parentPart;
    private final DashboardItem sourceItem;

    public DashboardViewDialog(DashboardViewContainer parentPart, DashboardItem sourceItem) {
        super(parentPart.getSite().getShell(), "View Dashboard", null);

        this.parentPart = parentPart;
        this.sourceItem = sourceItem;
    }

    @Override
    protected IDialogSettings getDialogBoundsSettings() {
        return UIUtils.getSettingsSection(UIDashboardActivator.getDefault().getDialogSettings(), DIALOG_ID);
    }

    @Override
    protected Composite createDialogArea(Composite parent) {
        Composite dialogArea = super.createDialogArea(parent);

        Composite chartGroup = UIUtils.createPlaceholder(dialogArea, 1);
        chartGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        chartGroup.setLayout(new FillLayout());

        DashboardListViewer dashboardListViewer = new DashboardListViewer(
            parentPart.getSite(),
            sourceItem.getDataSourceContainer(),
            parentPart.getViewConfiguration());
        dashboardListViewer.setSingleChartMode(true);
        dashboardListViewer.createControl(chartGroup);

        DashboardItem targetItem  = new DashboardItem(
            (DashboardList) dashboardListViewer.getDefaultGroup(),
            sourceItem.getDashboardId());
        targetItem.moveViewFrom(sourceItem, false);

        return dialogArea;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
    }

}