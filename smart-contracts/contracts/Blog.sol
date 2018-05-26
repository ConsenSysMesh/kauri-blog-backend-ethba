pragma solidity ^0.4.21;

import 'zeppelin-solidity/contracts/token/ERC721/ERC721Token.sol';

contract Blog is ERC721Token {
  event PostSubmitted(bytes32 indexed id, bytes32 ipfsHash, address indexed creator);
  event BlogTipped(address indexed tipper, address indexed author, uint amount);
  event PostTipped(bytes32 indexed id, address indexed tipper, uint amount);

  mapping (bytes32 => bool) postExists;
  mapping (uint => bytes32) tokenToPostId;
  uint numberOfPosts = 0;

  constructor(string _name, string _symbol) public ERC721Token(_name, _symbol) {
  }

  function submitPost(bytes32 _id, bytes32 _ipfsHash) public {
    require(postExists[_id] == false);
    postExists[_id] = true;
    numberOfPosts ++;
    tokenToPostId[numberOfPosts] = _id;
    _mint(msg.sender, numberOfPosts);
    emit PostSubmitted(_id, _ipfsHash, msg.sender);
  }

	function tipPost(bytes32 _id, address _author) public payable {
		require(postExists[_id] == true);
		_author.transfer(msg.value);
		emit PostTipped(_id, msg.sender, msg.value);
	}

	function tipBlog(address _author) public payable {
		_author.transfer(msg.value);
		emit BlogTipped(msg.sender, _author, msg.value);
	}

}
