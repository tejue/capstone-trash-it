import styled from "styled-components";

type SnackbarProps = {
    message: string;
    onClickOk: () => void;
    onClickCancel?: () => void;
}

export default function Snackbar(props: Readonly<SnackbarProps>) {

    return (
        <StyledSnack>
            <SnackMessage>{props.message}</SnackMessage>
            <SnackButton onClick={props.onClickOk}>Alright</SnackButton>
            {props.onClickCancel && <SnackButton onClick={props.onClickCancel}>Cancel</SnackButton>}
        </StyledSnack>
    );
}

const StyledSnack = styled.div`
  display: flex;
  flex-direction: column;
  gap: 15px;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  padding: 30px;
  background-color: rgba(54, 54, 54, 0.87);
  color: var(--text-color-light);
  text-align: center;
  z-index: 5;
`
const SnackMessage = styled.span`
  margin-bottom: 10px;
`

const SnackButton = styled.button`
  background-color: transparent;
  border: var(--secondary-color) solid thin;
  border-radius: 5px;
  color: var(--text-color-light);
  //margin: 0 40px 40px 40px;
  padding: 5px;
`
