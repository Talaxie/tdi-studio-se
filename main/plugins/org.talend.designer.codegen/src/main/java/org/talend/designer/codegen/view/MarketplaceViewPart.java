// ============================================================================
//
// Copyright (C) 2006-2021 Talaxie Inc. - www.deilink.fr
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talaxie SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.codegen.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.PlatformUI;
import org.talend.core.ui.webService.Webhook;
import org.talend.commons.ui.runtime.ColorConstants;
import org.talend.designer.codegen.CodeGeneratorActivator;
import org.talend.designer.codegen.components.ui.IComponentPreferenceConstant;

public class MarketplaceViewPart extends ViewPart {

  private static final Logger LOGGER = Logger.getLogger(MarketplaceViewPart.class);

  public static final String ID = "org.talend.designer.codegen.views.MarketplaceViewPart"; //$NON-NLS-1$

  private Display display = null;
  private ScrolledComposite scrolledComposite = null;
  private Text searchText = null;
  private List<HashMap<String, String>> components = null;

  /**
   * Constructor
   */
  public MarketplaceViewPart() {
    // TODO
  }

  @Override
  public void createPartControl(Composite container) {
    Composite parent = container;
    GridLayout gridLayout = new GridLayout(1, false);
    parent.setLayout(gridLayout);
    display = parent.getDisplay();
    parent.setBackground(ColorConstants.WHITE_COLOR);

    // Créez un composite pour afficher les détails de l'élément sélectionné
    Composite headerComposite = new Composite(parent, SWT.NONE);
    headerComposite.setLayout(new GridLayout(2, false));
    headerComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    headerComposite.setBackground(ColorConstants.WHITE_COLOR);

    searchText = new Text(headerComposite, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
    searchText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    searchText.setText("");
    searchText.setMessage("Search...");
    searchText.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.keyCode == SWT.CR){
          componentsInit();
        }
      }
    });
    searchText.addListener(SWT.DefaultSelection, event -> {
      if (event.detail == SWT.ICON_CANCEL) {
        componentsInit();
      }
    });

    Button searchButton = new Button(headerComposite, SWT.NONE);
    searchButton.setImage(ImageDescriptor.createFromURL(this.getClass().getResource("/icons/refresh.png")).createImage());
    searchButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
    searchButton.addListener(SWT.Selection, event -> componentsInit());

    scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
    scrolledComposite.setLayout(new FillLayout());
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    scrolledComposite.setLayoutData(data);
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);
    scrolledComposite.setBackground(ColorConstants.WHITE_COLOR);

    componentsInit();
  }

  @Override
  public void setFocus() {
    // TODO
  }

  private void componentsInit() {
    components = null;
    displayList();
    if (getComponentPath().equals("")) {
      return;
    }

    String search = searchText.getText();
    ExecutorService executor = Executors.newFixedThreadPool(1);
    executor.execute(() -> {
      try {
        components = Webhook.marketplaceComponents(search);
      } catch (Exception e) {
        e.printStackTrace();
      }
      displayList();
    });
  }

  private void displayList() {
    Control content = scrolledComposite.getContent();
    if (content != null) {
      display.syncExec(() -> {
        if (!content.isDisposed()) {
          content.dispose();
        }
      });
    }

    display.syncExec(() -> {
      Composite composite = new Composite(scrolledComposite, SWT.NONE);
      composite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
      composite.setLayout(new GridLayout(1, false));
      composite.setBackground(ColorConstants.WHITE_COLOR);

      if (getComponentPath().equals("")) {
        CLabel noComponentPathLabel = new CLabel(composite, SWT.NONE);
        noComponentPathLabel.setText("No component path defined !");
      } else if (components == null) {
        ProgressBar progressBar = new ProgressBar(composite, SWT.INDETERMINATE);
        progressBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        progressBar.setVisible(true);
      } else {
        for (HashMap<String, String> component : components) {
				  Composite titreComposite = new Composite(composite, SWT.NONE);
				  titreComposite.setLayout(new GridLayout(2, false));
				  titreComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, true, false));
				  titreComposite.setBackground(ColorConstants.WHITE_COLOR);
					
				  // Logo
				  Label imageLabel = new Label(titreComposite, SWT.NONE);
				  imageLabel.setImage(getImageFromWeb(component.get("image")));
				  imageLabel.setBackground(ColorConstants.WHITE_COLOR);
				  
		      // Titre
				  CLabel titleLabel = new CLabel(titreComposite, SWT.BOLD);
				  titleLabel.setFont(new org.eclipse.swt.graphics.Font(null, "Arial", 14, SWT.BOLD | SWT.ITALIC));
				  titleLabel.setForeground(new Color(96, 125, 139));
				  titleLabel.setText(component.get("name"));
          titleLabel.setBackground(ColorConstants.WHITE_COLOR);

          Composite itemComposite = new Composite(composite, SWT.NONE);
          itemComposite.setLayout(new GridLayout(2, false));
          itemComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, true, false));
          itemComposite.setBackground(ColorConstants.WHITE_COLOR);

          Composite leftComposite = new Composite(itemComposite, SWT.NONE);
          leftComposite.setLayout(new GridLayout(1, false));
          leftComposite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
          leftComposite.setBackground(ColorConstants.WHITE_COLOR);

          // Install
          Button installButton = new Button(leftComposite, SWT.NONE);
				  if (component.get("origine").equals("Talaxie")) {
					  installButton.setImage(ImageDescriptor.createFromURL(this.getClass().getResource("/icons/appli_16x16.png")).createImage());
				  } else if (component.get("origine").equals("Private")) {
					  installButton.setImage(ImageDescriptor.createFromURL(this.getClass().getResource("/icons/company_16x16.png")).createImage());
				  }
          installButton.setText("Installer");
          installButton.addListener (SWT.Selection, event -> componentInstall(component));
        
          // Version
          Label versionLabel = new Label(leftComposite, SWT.WRAP);
          versionLabel.setText(component.get("version") + " (" + component.get("release_Date") + ")");
          versionLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
          versionLabel.setForeground(new Color(200, 200, 200));
          versionLabel.setFont(new org.eclipse.swt.graphics.Font(null, "Arial", 7, SWT.ITALIC));
          versionLabel.setBackground(ColorConstants.WHITE_COLOR);

          Composite bodyComposite = new Composite(itemComposite, SWT.NONE);
          bodyComposite.setLayout(new GridLayout(1, false));
          bodyComposite.setBackground(ColorConstants.WHITE_COLOR);
  
          // Description
          Label descriptionLabel = new Label(bodyComposite, SWT.WRAP);
          descriptionLabel.setText(component.get("description"));
          descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
          descriptionLabel.setBackground(ColorConstants.WHITE_COLOR);

          // Item footer
          Composite itemheaderComposite = new Composite(bodyComposite, SWT.NONE);
          itemheaderComposite.setLayout(new GridLayout(2, false));
          itemheaderComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
          itemheaderComposite.setBackground(ColorConstants.WHITE_COLOR);

          // Author
          CLabel authorLabel = new CLabel(itemheaderComposite, SWT.BOLD);
          authorLabel.setFont(new org.eclipse.swt.graphics.Font(null, "Arial", 7, SWT.BOLD));
          authorLabel.setForeground(new Color(98, 168, 234));
          authorLabel.setText(component.get("author"));
          authorLabel.setBackground(ColorConstants.WHITE_COLOR);

          // Lien
          Link link = new Link(itemheaderComposite, SWT.NONE);
          link.setText("<a href=\"" + component.get("url") + "\">(see info)</a>");
          link.setBackground(ColorConstants.WHITE_COLOR);
          link.addListener(SWT.Selection, event -> openUrl(event.text));

          // Separator
          Label separatorLabel = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
          separatorLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        }
      }

      scrolledComposite.setContent(composite);
      Point size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
      scrolledComposite.setMinSize(size);
    });
  }

  private void openUrl(String url) {
    try {
      PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(url));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getComponentPath() {
    IPreferenceStore prefStore = CodeGeneratorActivator.getDefault().getPreferenceStore();
    String path = prefStore.getString(IComponentPreferenceConstant.USER_COMPONENTS_FOLDER);
    if (path == null) {
      return "";
    }
    return path;
  }

  private Image getImageFromWeb(String url) {
    try {
      URL imageUrl = new URL(url);
      ImageData imageData = new ImageData(imageUrl.openStream());
      return new Image(display, imageData);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Boolean componentInstall(HashMap<String, String> component) {
    // TODO
    return true;
  }
}
