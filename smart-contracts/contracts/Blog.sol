pragma solidity ^0.4.23;

import 'zeppelin-solidity/contracts/token/ERC721/ERC721Token.sol';

contract Blog is ERC721Token {
  event PostSubmitted(bytes32 indexed id, bytes32 ipfsHash, address indexed creator);
  event BlogTipped(address indexed tipper, address indexed author, uint amount);
  event PostTipped(bytes32 indexed id, address indexed tipper, uint amount);

  mapping (bytes32 => bool) postExists;
  uint numberOfPosts = 0;

	constructor() public {
		owner = msg.sender;
	}

  function submitPost(bytes32 _id, bytes32 _ipfsHash) {
    require(postExists[_id] == false);
    postExists[_id] = true;
    numberOfPosts ++;
    _mint(msg.sender, numberOfPosts);
    emit PostSubmitted(_id, _ipfsHash, msg.sender);
  }

	function tipPost(bytes32 _id, address _author) {
		require(postExists[_id] == true);
		_author.transfer(msg.value);
		emit PostTipped(_id, msg.sender, msg.value);
	}

	function tipBlog(bytes32 _id, address _author) {
		_author.transfer(msg.value);
		emit BlogTipped(msg.sender, _author, msg.value);
	}

}

