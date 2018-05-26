pragma solidity ^0.4.23;

contract Blog {
  event PostSubmitted(bytes32 indexed id, bytes32 ipfsHash, address indexed creator);
  event BlogTipped(address indexed tipper, address indexed author, uint amount);
  event PostTipped(bytes32 indexed id, address indexed tipper, uint amount);

  mapping (bytes32 => bool) postExists;

  constructor() public {
    // owner = msg.sender;
  }

  // modifier restricted() {
  //   if (msg.sender == owner) _;
  // }

  function submitPost(bytes32 _id, bytes32 _ipfsHash) {
    require(postExists[_id] == false);
    postExists[_id] = true;
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
