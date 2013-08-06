package edu.njust.sem.log.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class TreeModelFactory {
	public static TreeModel getTreeModel(int i){
		DefaultTreeModel model1 = new DefaultTreeModel(
				new DefaultMutableTreeNode("Lights & Lighting2") {
					{
						DefaultMutableTreeNode node_1;
						node_1 = new DefaultMutableTreeNode(
								"LED Lighting & Display");
						
						node_1.add(new DefaultMutableTreeNode(
								"LED Display"));
						node_1.add(new DefaultMutableTreeNode(
								"LED Lighting"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Indoor & Outdoor Lighting");
						node_1.add(new DefaultMutableTreeNode(
								"Interior Lighting"));
						node_1.add(new DefaultMutableTreeNode(
								"Outdoor Lighting"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Stage Lighting");
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Lighting Decoration");
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Professional Lighting");
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Lighting Bulbs & Tubes");
						
						node_1.add(new DefaultMutableTreeNode(
								"Bulb & Lamp"));
						node_1.add(new DefaultMutableTreeNode(
								"Compact Bulb & Lamp"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Lighting Accessories");
						node_1.add(new DefaultMutableTreeNode(
								"Lighting Fixtures"));
						add(node_1);
					}
				});
		
		DefaultTreeModel model2 = new DefaultTreeModel(
				new DefaultMutableTreeNode("Lights & Lighting2") {
					{
						DefaultMutableTreeNode node_1;
						node_1 = new DefaultMutableTreeNode(
								"LED Lighting & Display");
						
						node_1.add(new DefaultMutableTreeNode(
								"LED Display"));
						node_1.add(new DefaultMutableTreeNode(
								"LED Lighting"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Indoor & Outdoor Lighting");
						node_1.add(new DefaultMutableTreeNode(
								"Interior Lighting"));
						node_1.add(new DefaultMutableTreeNode(
								"Outdoor Lighting"));
						node_1.add(new DefaultMutableTreeNode(
								"Lighting Decoration"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Professional Lighting");
						node_1.add(new DefaultMutableTreeNode(
								"Stage Lighting"));
						add(node_1);
						
						node_1 = new DefaultMutableTreeNode(
								"Lighting Bulbs & Tubes");
						
						node_1.add(new DefaultMutableTreeNode(
								"Bulb & Lamp"));
						node_1.add(new DefaultMutableTreeNode(
								"Compact Bulb & Lamp"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Lighting Accessories");
						node_1.add(new DefaultMutableTreeNode(
								"Lighting Fixtures"));
						add(node_1);
					}
				});
		
		DefaultTreeModel model3 = new DefaultTreeModel(
				new DefaultMutableTreeNode("Lights & Lighting2") {
					{
						DefaultMutableTreeNode node_1;
						node_1 = new DefaultMutableTreeNode(
								"LED Lighting & Display");
						
						node_1.add(new DefaultMutableTreeNode(
								"LED Display"));
						node_1.add(new DefaultMutableTreeNode(
								"LED Lighting"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Indoor & Outdoor Lighting");
						node_1.add(new DefaultMutableTreeNode(
								"Interior Lighting"));
						node_1.add(new DefaultMutableTreeNode(
								"Outdoor Lighting"));
						node_1.add(new DefaultMutableTreeNode(
								"Lighting Decoration"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Professional Lighting");
						node_1.add(new DefaultMutableTreeNode(
								"Stage Lighting"));
						add(node_1);
						
						node_1 = new DefaultMutableTreeNode(
								"Lighting Bulbs & Tubes");
						
						node_1.add(new DefaultMutableTreeNode(
								"Bulb & Lamp"));
						node_1.add(new DefaultMutableTreeNode(
								"Compact Bulb & Lamp"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Lighting Accessories");
						add(node_1);
						node_1 = new DefaultMutableTreeNode(
								"Lighting Fixtures");
						add(node_1);
					}
				});
		
		if ( i == 1){
			return model1;
		}else if(i == 2){
			return model2;
		}else{
			return model3;
		}
	}
}
