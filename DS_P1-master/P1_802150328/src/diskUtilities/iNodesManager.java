package diskUtilities;

import java.util.ArrayList;
/**
 * Class that manages and has methods related to the iNodes in the disk
 * @author Francisco Diaz
 *
 */
public class iNodesManager {

	
	
	/**
	 * Gets the first data block from an i-node using its index.
	 * @param d DiskUnit in use.
	 * @param iNodeIndex Index of the i-node to get data block number.
	 * @return Returns the data block from the i-node with index iNodeIndex
	 */
	public static int getFirstDataBlockFromiNode(DiskUnit d, int iNodeIndex) {
		
		int blockSize = d.getBlockSize();
		ArrayList<Integer> iNodeInfo = getiNodePosition(iNodeIndex, blockSize);
		int iNodeBlockNum = iNodeInfo.get(0); // get blockNum of the iNode 
		int iNodeBytePos = iNodeInfo.get(1);  // get iNode byte position
		VirtualDiskBlock vdb = new VirtualDiskBlock(blockSize);
		d.read(iNodeBlockNum, vdb);
		
		return DiskUtils.getIntFromBlock(vdb, iNodeBytePos);
	}
	
	/**
	 * Sets the first data block from an i-node using its index.
	 * @param d DiskUnit in use
	 * @param iNodeIndex Index of the i-node to set data block number.
	 * @param newDataBlock new data block number to set into i-node.
	 */
	public static void setDataBlockToINode(DiskUnit d, int iNodeIndex, int newDataBlock) {
		
		int blockSize = d.getBlockSize();
		ArrayList<Integer> iNodeInfo = getiNodePosition(iNodeIndex, blockSize);
		int iNodeBlockNum = iNodeInfo.get(0); // get blockNum of the iNode 
		int iNodeBytePos = iNodeInfo.get(1);  // get iNode byte position
		VirtualDiskBlock vdb = DiskUtils.copyBlockToVDB(d, iNodeBlockNum);
		
		DiskUtils.copyIntToBlock(vdb, iNodeBytePos, newDataBlock);
		d.write(iNodeBlockNum, vdb);
	}
	/**
	 * Returns ArrayList with the block number of where the i-node is stored and
	 * byte position of where the iNode is in the block.
	 * @param iNodeIndex Index of the iNode
	 * @param blockSize Bytes per block
	 * @return ArrayList with the block number of where the i-node is stored and
	 * byte position of where the iNode is in the block 
	 */
	public static ArrayList<Integer> getiNodePosition(int iNodeIndex, int blockSize) {
		
		int nodesPerBlock = blockSize / 9;
		int blockNumber = (1 + (iNodeIndex / nodesPerBlock)); // block number of where the i-node is stored
		int iNodeBytePos = ((iNodeIndex % nodesPerBlock) * 9); // byte position of where the iNode is in the block
		
		ArrayList<Integer> nodeArray = new ArrayList<>();
		nodeArray.add(blockNumber);
		nodeArray.add(iNodeBytePos);
		
		return nodeArray;
	}
	
	/**
	 * Gets the size of a file into its i-node
	 * @param d DiskUnit in use
	 * @param iNodeIndex Index of the iNode to modify
	 * @return Returns size of the file the i-node makes reference to.
	 */
	public static int getSizeiNode(DiskUnit d, int iNodeIndex) {
		
		int blockSize = d.getBlockSize();
		ArrayList<Integer> iNodeInfo = getiNodePosition(iNodeIndex, blockSize);
		int iNodeBlockNum = iNodeInfo.get(0); // get blockNum of the iNode 
		int iNodeBytePos = iNodeInfo.get(1);  // get iNode byte position
		VirtualDiskBlock vdb = DiskUtils.copyBlockToVDB(d, iNodeBlockNum);
		
		// Get size in the next 4 bytes after the beginning of the i-node byte position
		return DiskUtils.getIntFromBlock(vdb, iNodeBytePos+4);
		
	}
	/**
	 * Sets the size of a file into is i-node
	 * @param d DiskUnit in use
	 * @param iNodeIndex Index of the INode to modify
	 * @param sizeValue Size of the file which the i-node makes reference to.
	 */
	public static void setSizeOfiNode(DiskUnit d, int iNodeIndex, int sizeValue) {
		
		int blockSize = d.getBlockSize();
		ArrayList<Integer> iNodeInfo = getiNodePosition(iNodeIndex, blockSize);
		int iNodeBlockNum = iNodeInfo.get(0); // get blockNum of the iNode 
		int iNodeBytePos = iNodeInfo.get(1);  // get iNode byte position
		VirtualDiskBlock vdb = DiskUtils.copyBlockToVDB(d, iNodeBlockNum);
		
		// Set size in the next 4 bytes after the beginning of the i-node byte position
		DiskUtils.copyIntToBlock(vdb, iNodeBytePos+4, sizeValue); 
		d.write(iNodeBlockNum, vdb);  // Write block into disk
	}
	
	/**
	 * Gets the next free i-node.
	 * @return Index of the next free i-node
	 */
	public static int getFreeiNode(DiskUnit d) throws Exception {
		int freeiNodePos = d.getFirstFreeINode();  // Get a free i-node index
		if (freeiNodePos == 0)
			throw new Exception("No more I-Nodes available");
		int nextFreeINodeIdx = getFirstDataBlockFromiNode(d, freeiNodePos); 
		d.setFirstFreeINode(nextFreeINodeIdx); // Set the reference to the next free i-node into the disk (like linked list)	
		
		return freeiNodePos;
	}
	
	
	
	
	
}
