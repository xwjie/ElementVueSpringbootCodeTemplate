package cn.xiaowenjie.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class TreeNode {

	private long id;

	private String name;

	private List<TreeNode> subnodes;

	public TreeNode(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
