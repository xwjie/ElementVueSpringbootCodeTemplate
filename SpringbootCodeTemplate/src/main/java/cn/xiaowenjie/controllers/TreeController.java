package cn.xiaowenjie.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaowenjie.beans.TreeNode;
import cn.xiaowenjie.common.beans.ResultBean;

@RestController
@CrossOrigin
@RequestMapping("/tree")
public class TreeController {

	@GetMapping("/simple")
	public ResultBean<List<TreeNode>> simple() {

		List<TreeNode> nodes = createSimpleNodes();

		return new ResultBean<>(nodes);
	}

	private List<TreeNode> createSimpleNodes() {
		List<TreeNode> nodes = createNodes("第1层节点");

		for (int i = 0; i < nodes.size(); i++) {
			TreeNode n = nodes.get(i);

			if (i > 2) {
				n.setSubnodes(createNodes("第2层节点"));
			}
		}
		return nodes;
	}

	private List<TreeNode> createNodes(String name) {
		List<TreeNode> nodes2 = new ArrayList<>();
		for (int j = 0; j < 5; j++) {
			TreeNode n2 = new TreeNode(j, name + j);
			nodes2.add(n2);

		}
		return nodes2;
	}
}
